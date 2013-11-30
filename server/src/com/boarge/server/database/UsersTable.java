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
	private static final String COL_Id = "id";
	private static final String COL_UserName = "name";

	public static void init(Connection conn)
	{
		s_connection = conn;
		try
		{
			createUsersDBTable();
			// createUser("Hussain");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		// catch (JSONException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static void createUsersDBTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name + " (" + COL_Id
				+ " INTEGER NOT NULL, " + COL_UserName + " TEXT PRIMARY KEY NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createUser(String userName) throws SQLException, JSONException
	{
		String sql = "INSERT INTO " + TABLE_Name + " (" + COL_Id + ", " + COL_UserName
				+ ") VALUES ((SELECT IFNULL(MAX(id), 0) + 1 FROM " + TABLE_Name + ")" + ", "
				+ UtilsDB.getSqlVal(userName) + ");";
		// String sql = "INSERT INTO Users ('JBoblitt'
		// + ") VALUES ((SELECT IFNULL(MAX(id), 0) + 1 FROM Users)"
		// + UtilsDB.getSqlVal(userName) + ");";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();

		// Return game created without querying table again.
		/*
		 * JSONObject userCreated = UtilsJSON.getJSON(getLastInsertId(), "none",
		 * 0, isPrivate, isRanked, difficulty, numTeams, numPlayersPerTeam,
		 * timeLimitPerMove, turnStrategy); return gameCreated.toString();
		 */
		return "new user";
	}

	public static void removeUser(String userName) throws SQLException
	{
		String sql = "DELETE FROM " + TABLE_Name + " WHERE " + COL_UserName + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, userName);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getUser(String userName) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_UserName + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, userName);
		ResultSet rs = stmt.executeQuery();
		String gameJSON = null;
		if (rs.next())
		{
			JSONArray array = new JSONArray();
			array.put(getUserFromJSONResultSet(rs));
			gameJSON = array.toString();
		}
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

	private static String getArrayUsersJSONResultSet(ResultSet resultingGames)
			throws JSONException, SQLException
	{
		JSONArray gamesArrayJSON = new JSONArray();
		while (resultingGames.next())
		{
			gamesArrayJSON.put(getUserFromJSONResultSet(resultingGames));
		}
		if (gamesArrayJSON.length() == 0)
			return null;
		return gamesArrayJSON.toString();
	}

	private static JSONObject getUserFromJSONResultSet(ResultSet resultGame) throws JSONException,
			SQLException
	{
		int id = resultGame.getInt(COL_Id);
		String userName = resultGame.getString(COL_UserName);
		return UtilsJSON.getJSON(id, userName);
	}
}
