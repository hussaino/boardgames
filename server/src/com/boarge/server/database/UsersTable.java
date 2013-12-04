package com.boarge.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boarge.server.utils.UtilsDB;
import com.boarge.server.utils.UtilsJSON;

public class UsersTable
{
	private static Connection s_connection;

	private static final String TABLE_Name = "Users";
	public static final String COL_Id = "UserId";
	private static final String COL_UserName = "Name";

	public static void init(Connection conn)
	{
		s_connection = conn;
		try
		{
			createUsersDBTable();
			createOrLoginUser("jboblitt");
			createOrLoginUser("arhea");
			createOrLoginUser("hussain");
			createOrLoginUser("whasup98");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	public static void createUsersDBTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name + " (" + COL_Id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_UserName
				+ " TEXT NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createOrLoginUser(String userName) throws SQLException, JSONException
	{
		String user = getUser(userName);
		if (user == null)
		{
			String sql = "INSERT INTO " + TABLE_Name + " (" + COL_UserName + ") VALUES ("
					+ UtilsDB.getSqlVal(userName) + ");";
			PreparedStatement stmt = s_connection.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();

			// Return game created without querying table again.
			int createdUserId = UtilsDB.getLastInsertId(s_connection);
			JSONArray arrayWrapper = new JSONArray();
			JSONObject userJSON = UtilsJSON.getJSON(createdUserId, userName);
			arrayWrapper.put(userJSON);
			return arrayWrapper.toString();
		}
		else
		{
			return user;
		}
	}

	public static void removeUser(int userId) throws SQLException
	{
		String sql = "DELETE FROM " + TABLE_Name + " WHERE " + COL_Id + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		stmt.close();
	}

	public static void removeUser(String userName) throws SQLException
	{
		String sql = "DELETE FROM " + TABLE_Name + " WHERE " + COL_UserName + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getUser(int userId) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, userId);
		return getUserUsingStmt(stmt);
	}

	public static String getUser(String userName) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_UserName + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, userName);
		return getUserUsingStmt(stmt);
	}

	private static String getUserUsingStmt(PreparedStatement stmt) throws SQLException,
			JSONException
	{
		ResultSet rs = stmt.executeQuery();
		String gameJSON = getArrayUsersJSONResultSet(rs);
		stmt.close();
		return gameJSON;
	}

	public static String getAllUsers() throws SQLException, JSONException
	{
		PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM " + TABLE_Name + ";");
		ResultSet rs = stmt.executeQuery();
		String games = getArrayUsersJSONResultSet(rs);
		rs.close();
		stmt.close();
		return games;
	}

	public static String getUsersInGame(int gameId) throws SQLException, JSONException
	{
		String sql = "SELECT " + TABLE_Name + ".* FROM " + UsersGamesTable.TABLE_Name + " JOIN "
				+ TABLE_Name + " USING " + "(" + COL_Id + ") WHERE " + UsersGamesTable.TABLE_Name
				+ "." + UsersGamesTable.COL_GameId + "=?;";

		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, gameId);
		ResultSet rs = stmt.executeQuery();
		String games = getArrayUsersJSONResultSet(rs);
		rs.close();
		stmt.close();
		return games;
	}

	private static String getArrayUsersJSONResultSet(ResultSet resultingGames)
			throws JSONException, SQLException
	{
		JSONArray gamesArrayJSON = new JSONArray();
		while (resultingGames.next())
		{
			int id = resultingGames.getInt(COL_Id);
			String userName = resultingGames.getString(COL_UserName);
			gamesArrayJSON.put(UtilsJSON.getJSON(id, userName));
		}
		if (gamesArrayJSON.length() == 0)
			return null;
		return gamesArrayJSON.toString();
	}

	public static int getNumUsersInGame(int gameId) throws JSONException, SQLException
	{
		String strUsersInGame = getUsersInGame(gameId);
		return strUsersInGame != null ? new JSONArray(strUsersInGame).length() : 0;
	}

	public static boolean userExists(int userId) throws SQLException, JSONException
	{
		return getUser(userId) != null;
	}
}
