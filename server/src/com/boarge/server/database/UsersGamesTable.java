package com.boarge.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;

import com.boarge.server.utils.UtilsDB;

public class UsersGamesTable
{
	private static Connection s_connection;

	public static final String TABLE_Name = "UsersGames";
	public static final String COL_Id = "id";
	public static final String COL_UserId = UsersTable.COL_Id;
	public static final String COL_GameId = GamesTable.COL_Id;

	public static void init(Connection conn)
	{
		s_connection = conn;
		try
		{
			createTable();
			// User 1 and 2 are playing in game 1
			// createRow(1, 1);
			// createRow(2, 1);
			//
			// // User 2 and 4 are playing in game 2
			// createRow(2, 2);
			// createRow(3, 2);
			//
			// // User 2, 3, 4 are playing in game 3
			// createRow(2, 3);
			// createRow(3, 3);
			// createRow(4, 3);
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

	public static void createTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name + " (" + COL_Id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_UserId
				+ " INTEGER NOT NULL, " + COL_GameId + " INTEGER NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createRow(int userId, int gameId) throws SQLException, JSONException
	{
		// If either the user doens't exist or the game doesn't exist, fail.
		if (!UsersTable.userExists(userId) || !GamesTable.gameExists(gameId))
			return null;
		// If user has already joined this game, return success
		if (hasRow(userId, gameId))
			return "200";
		// If user has not joined this game, but the game is full, return fail.
		if (!isGameOpen(gameId))
			return null;

		String sql = "INSERT INTO " + TABLE_Name + " (" + COL_UserId + ", " + COL_GameId
				+ ") VALUES (" + UtilsDB.getSqlVal(userId) + ", " + UtilsDB.getSqlVal(gameId)
				+ ");";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();

		return "200";
	}

	public static boolean isGameOpen(int gameId) throws JSONException, SQLException
	{
		return UsersTable.getNumUsersInGame(gameId) < GamesTable.getMaxPlayersForGame(gameId);
	}

	private static boolean hasRow(int userId, int teamId) throws SQLException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_UserId + "=? AND "
				+ COL_GameId + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, userId);
		stmt.setInt(2, teamId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
		{
			return true;
		}
		stmt.close();

		return false;
	}

	public static void removeRow(int id) throws SQLException
	{
		String sql = "DELETE FROM " + TABLE_Name + " WHERE " + COL_Id + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getRow(Integer rowId) throws SQLException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, rowId);
		ResultSet rs = stmt.executeQuery();
		return getArrayRows(rs);
	}

	public static String getAllRows() throws SQLException
	{
		String sql = "SELECT * FROM " + TABLE_Name + ";";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		return getArrayRows(rs);
	}

	private static String getArrayRows(ResultSet resultingGames) throws SQLException
	{
		String response = "";
		while (resultingGames.next())
		{
			response += "{id=";
			response += resultingGames.getInt(COL_Id) + " : usr=";
			response += resultingGames.getString(COL_UserId) + " : gme=";
			response += resultingGames.getString(COL_GameId) + "}\n";
		}

		return response;
	}
}
