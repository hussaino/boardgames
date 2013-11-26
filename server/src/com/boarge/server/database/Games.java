package com.boarge.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Games
{
	private static Connection s_connection;

	public static void init()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			s_connection = DriverManager.getConnection("jdbc:sqlite:boards.db");
			System.out.println("Opened database");

			createBoardsTable();

			if (getAllGames().length() < 1)
			{
				addBoard("Board object 1 JSON string");
				addBoard("Board object 2 JSON string");
				addBoard("Board object 3 JSON string");
			}

		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void createBoardsTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS boards (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, board TEXT NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static void addBoard(String board) throws SQLException
	{
		PreparedStatement stmt = s_connection.prepareStatement("INSERT INTO boards (board) "
				+ "VALUES ('" + board + "');");
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getGame(Integer gameID)
	{
		return null;
	}

	public static String getOpenGames(String gameCriteria)
	{
		return null;
	}

	public static String getUserTurns(String userID)
	{
		return null;
	}

	public static String getUserGames(String userID)
	{
		return null;
	}

	public static String getTeamTurns(String teamID)
	{
		return null;
	}

	public static String getTeamGames(String teamID)
	{
		return null;
	}

	public static String getAllGames()
	{
		String boards = "";
		try
		{
			PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM boards;");
			ResultSet rs = stmt.executeQuery();

			boards = getGamesJSONResultSet(rs);

			rs.close();
			stmt.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return boards;
	}

	private static String getGamesJSONResultSet(ResultSet resultingGames) throws JSONException,
			SQLException
	{
		JSONArray gamesArrayJSON = new JSONArray();
		while (resultingGames.next())
		{
			gamesArrayJSON.put(new JSONObject(resultingGames.getString("board")));
		}
		return gamesArrayJSON.toString();
	}
}
