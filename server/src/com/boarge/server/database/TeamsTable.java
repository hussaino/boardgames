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

public class TeamsTable
{
	private static Connection s_connection;

	private static final String TABLE_Name = "Teams";
	private static final String COL_Id = "id";
	private static final String COL_TeamName = "name";

	public static void init(Connection conn)
	{
		s_connection = conn;
		try
		{
			createTeamsDBTable();
			// createTeam("Ramrod");
			// createTeam("Oaf Squad");
			// createTeam("City Shlickers");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		// catch (JSONException e)
		// {
		// e.printStackTrace();
		// }

	}

	public static void createTeamsDBTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name + " (" + COL_Id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_TeamName
				+ " TEXT NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createTeam(String teamName) throws SQLException, JSONException
	{
		String sql = "INSERT INTO " + TABLE_Name + " (" + COL_TeamName + ") VALUES ("
				+ UtilsDB.getSqlVal(teamName) + ");";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();

		// Return game created without querying table again.
		int createdTeamId = UtilsDB.getLastInsertId(s_connection);
		JSONArray arrayWrapper = new JSONArray();
		arrayWrapper.put(UtilsJSON.getTeamJSON(createdTeamId, teamName));
		return arrayWrapper.toString();

	}

	public static void removeTeam(int teamId) throws SQLException
	{
		String sql = "DELETE FROM " + TABLE_Name + " WHERE " + COL_Id + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, teamId);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getTeam(int teamId) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, teamId);
		return getTeamUsingStmt(stmt);
	}

	private static String getTeamUsingStmt(PreparedStatement stmt) throws SQLException,
			JSONException
	{
		ResultSet rs = stmt.executeQuery();
		String gameJSON = null;
		if (rs.next())
		{
			JSONArray array = new JSONArray();
			array.put(getTeamFromJSONResultSet(rs));
			gameJSON = array.toString();
		}
		stmt.close();

		return gameJSON;
	}

	public static String getAllTeams() throws SQLException, JSONException
	{
		PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM " + TABLE_Name + ";");
		ResultSet rs = stmt.executeQuery();
		String games = getArrayTeamsJSONResultSet(rs);
		rs.close();
		stmt.close();
		return games;
	}

	private static String getArrayTeamsJSONResultSet(ResultSet resultingGames)
			throws JSONException, SQLException
	{
		JSONArray gamesArrayJSON = new JSONArray();
		while (resultingGames.next())
		{
			gamesArrayJSON.put(getTeamFromJSONResultSet(resultingGames));
		}
		if (gamesArrayJSON.length() == 0)
			return null;
		return gamesArrayJSON.toString();
	}

	private static JSONObject getTeamFromJSONResultSet(ResultSet resultGame) throws JSONException,
			SQLException
	{
		int id = resultGame.getInt(COL_Id);
		String teamName = resultGame.getString(COL_TeamName);
		return UtilsJSON.getTeamJSON(id, teamName);
	}
}
