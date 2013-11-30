package com.boarge.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boarge.server.temp.UtilsJSON;

public class GamesTable
{
	private static Connection s_connection;

	private static final String TABLE_Name = "Games";
	private static final String COL_Id = "id";
	private static final String COL_GameState = "GameState";
	private static final String COL_Turn = "Turn";
	private static final String COL_Private = "Private";
	private static final String COL_Ranked = "Ranked";
	private static final String COL_Difficulty = "Difficulty";
	private static final String COL_NumberTeams = "NumberTeams";
	private static final String COL_PlayersPerTeams = "PlayersPerTeam";
	private static final String COL_TimeLimit = "TimeLimit";
	private static final String COL_TurnStrategy = "TurnStrategy";

	public static void init()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			s_connection = DriverManager.getConnection("jdbc:sqlite:" + TABLE_Name + ".db");
			System.out.println("Opened database");

			createGamesDBTable();
			// createGame(true, false, 9, 4, 2, 10, 1);
			// createGame(false, true, 3, 2, 1, -1, -1);
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void createGamesDBTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_GameState
				+ " TEXT NOT NULL, " + COL_Turn + " INTEGER NOT NULL, " + COL_Private
				+ " INTEGER NOT NULL, " + COL_Ranked + " INTEGER NOT NULL, " + COL_Difficulty
				+ " INTEGER NOT NULL, " + COL_NumberTeams + " INTEGER NOT NULL, "
				+ COL_PlayersPerTeams + " INTEGER NOT NULL, " + COL_TimeLimit
				+ " INTEGER NOT NULL, " + COL_TurnStrategy + " INTEGER NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createGame(boolean isPrivate, boolean isRanked, int difficulty,
			int numTeams, int numPlayersPerTeam, int timeLimitPerMove, int turnStrategy)
			throws SQLException, JSONException
	{
		String sql = "INSERT INTO " + TABLE_Name + " (" + COL_GameState + ", " + COL_Turn + ", "
				+ COL_Private + ", " + COL_Ranked + ", " + COL_Difficulty + ", " + COL_NumberTeams
				+ ", " + COL_PlayersPerTeams + ", " + COL_TimeLimit + ", " + COL_TurnStrategy
				+ ") VALUES ('none', 0, " + getSqlVal(isPrivate) + "," + getSqlVal(isRanked) + ","
				+ getSqlVal(difficulty) + "," + getSqlVal(numTeams) + ","
				+ getSqlVal(numPlayersPerTeam) + "," + getSqlVal(timeLimitPerMove) + ","
				+ getSqlVal(turnStrategy) + ");";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();

		// Return game created without querying table again.
		JSONObject gameCreated = UtilsJSON.getJSON(getLastInsertId(), "none", 0, isPrivate,
				isRanked, difficulty, numTeams, numPlayersPerTeam, timeLimitPerMove, turnStrategy);
		return gameCreated.toString();
	}

	private static int getLastInsertId() throws SQLException
	{
		String lastInsertCol = "last_insert_rowid()";
		String sql = "SELECT " + lastInsertCol;
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		int id = rs.getInt(lastInsertCol);
		stmt.close();
		return id;
	}

	private static String getSqlVal(boolean bool)
	{
		return bool ? "1" : "0";
	}

	private static String getSqlVal(Integer num)
	{
		return String.valueOf(num);
	}

	public static void updateGameState(int gameId, String gameState) throws SQLException
	{
		String sql = "UPDATE " + TABLE_Name + " SET " + COL_GameState + "=? WHERE " + COL_Id
				+ "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, gameState);
		stmt.setInt(2, gameId);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getGame(Integer gameId) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, gameId);
		ResultSet rs = stmt.executeQuery();
		String gameJSON = null;
		if (rs.next())
		{
			JSONArray array = new JSONArray();
			array.put(getGameJSONResultSet(rs));
			gameJSON = array.toString();
		}
		stmt.close();

		return gameJSON;
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

	public static String getAllGames() throws SQLException, JSONException
	{
		PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM " + TABLE_Name + ";");
		ResultSet rs = stmt.executeQuery();
		String games = getArrayGamesJSONResultSet(rs);
		rs.close();
		stmt.close();
		return games;
	}

	private static String getArrayGamesJSONResultSet(ResultSet resultingGames)
			throws JSONException, SQLException
	{
		JSONArray gamesArrayJSON = new JSONArray();
		while (resultingGames.next())
		{
			gamesArrayJSON.put(getGameJSONResultSet(resultingGames));
		}
		if (gamesArrayJSON.length() == 0)
			return null;
		return gamesArrayJSON.toString();
	}

	private static JSONObject getGameJSONResultSet(ResultSet resultGame) throws JSONException,
			SQLException
	{
		int id = resultGame.getInt(COL_Id);
		String gameState = resultGame.getString(COL_GameState);
		int turn = resultGame.getInt(COL_Turn);
		boolean isPrivate = resultGame.getBoolean(COL_Private);
		boolean isRanked = resultGame.getBoolean(COL_Ranked);
		int difficulty = resultGame.getInt(COL_Difficulty);
		int numTeams = resultGame.getInt(COL_NumberTeams);
		int numPlayersPerTeam = resultGame.getInt(COL_PlayersPerTeams);
		int timeLimitPerMove = resultGame.getInt(COL_TimeLimit);
		int turnStrategy = resultGame.getInt(COL_TurnStrategy);
		return UtilsJSON.getJSON(id, gameState, turn, isPrivate, isRanked, difficulty, numTeams,
				numPlayersPerTeam, timeLimitPerMove, turnStrategy);
	}
}
