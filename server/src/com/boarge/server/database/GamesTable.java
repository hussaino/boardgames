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

public class GamesTable
{
	private static Connection s_connection;

	private static final String TABLE_Name = "Games";
	public static final String COL_Id = "GameId";
	private static final String COL_GameState = "GameState";
	private static final String COL_Turn = "Turn";
	private static final String COL_Private = "Private";
	private static final String COL_Ranked = "Ranked";
	private static final String COL_Difficulty = "Difficulty";
	private static final String COL_NumberTeams = "NumberTeams";
	private static final String COL_PlayersPerTeams = "PlayersPerTeam";
	private static final String COL_TimeLimit = "TimeLimit";
	private static final String COL_TurnStrategy = "TurnStrategy";

	public static void init(Connection conn)
	{
		s_connection = conn;
		try
		{
			createGamesDBTable();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		// try
		// {
		// createGame(false, true, 9, 2, 1, 10, 1, 1);
		// createGame(false, false, 3, 2, 1, -1, -1, 2);
		// createGame(false, true, 3, 3, 1, -1, -1, 3);
		// }
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// }
		// catch (JSONException e)
		// {
		// e.printStackTrace();
		// }

	}

	public static void createGamesDBTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_Name + " (" + COL_Id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_GameState
				+ " TEXT NOT NULL, " + COL_Turn + " INTEGER NOT NULL, " + COL_Private
				+ " INTEGER NOT NULL, " + COL_Ranked + " INTEGER NOT NULL, " + COL_Difficulty
				+ " INTEGER NOT NULL, " + COL_NumberTeams + " INTEGER NOT NULL, "
				+ COL_PlayersPerTeams + " INTEGER NOT NULL, " + COL_TimeLimit
				+ " INTEGER NOT NULL, " + COL_TurnStrategy + " INTEGER NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static String createGame(boolean isPrivate, boolean isRanked, int difficulty,
			int numTeams, int numPlayersPerTeam, int timeLimitPerMove, int turnStrategy, int userId)
			throws SQLException, JSONException
	{
		// Insert newly made game into Games table
		String sql = "INSERT INTO " + TABLE_Name + " (" + COL_GameState + ", " + COL_Turn + ", "
				+ COL_Private + ", " + COL_Ranked + ", " + COL_Difficulty + ", " + COL_NumberTeams
				+ ", " + COL_PlayersPerTeams + ", " + COL_TimeLimit + ", " + COL_TurnStrategy
				+ ") VALUES ('none', 0, " + UtilsDB.getSqlVal(isPrivate) + ","
				+ UtilsDB.getSqlVal(isRanked) + "," + UtilsDB.getSqlVal(difficulty) + ","
				+ UtilsDB.getSqlVal(numTeams) + "," + UtilsDB.getSqlVal(numPlayersPerTeam) + ","
				+ UtilsDB.getSqlVal(timeLimitPerMove) + "," + UtilsDB.getSqlVal(turnStrategy)
				+ ");";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();

		int gameIdInserted = UtilsDB.getLastInsertId(s_connection);

		// Insert relation between user and game into UsersGames table
		UsersGamesTable.createRow(userId, gameIdInserted);

		// Return game created without querying table again. Always return array
		// of games from servlet.
		String usersInGame = UsersTable.getUsersInGame(gameIdInserted);
		JSONObject gameCreated = UtilsJSON.getJSON(gameIdInserted, "none", 0, isPrivate, isRanked,
				difficulty, numTeams, numPlayersPerTeam, timeLimitPerMove, turnStrategy,
				usersInGame);
		JSONArray arrayWrapper = new JSONArray();
		arrayWrapper.put(gameCreated);
		return arrayWrapper.toString();
	}

	public static void updateGameState(int gameId, String gameState, int nextTurn)
			throws SQLException
	{
		String sql = "UPDATE " + TABLE_Name + " SET " + COL_GameState + "=?, " + COL_Turn
				+ "=? WHERE " + COL_Id + "=?;";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setString(1, gameState);
		stmt.setInt(2, nextTurn);
		stmt.setInt(3, gameId);
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getGame(Integer gameId) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, gameId);
		ResultSet rs = stmt.executeQuery();
		String gameJSON = getArrayGamesJSONResultSet(rs);
		stmt.close();
		return gameJSON;
	}

	public static String getOpenGames() throws SQLException, JSONException
	{
		PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM " + TABLE_Name + ";");
		ResultSet rs = stmt.executeQuery();
		JSONArray gamesArray = new JSONArray();
		while (rs.next())
		{
			int gameId = rs.getInt(COL_Id);
			if (UsersGamesTable.isGameOpen(gameId))
			{
				gamesArray.put(getGameJSONResultSet(rs));
			}
		}
		String games = gamesArray.toString();
		rs.close();
		stmt.close();
		return games;
	}

	public static String getUserTurns(int userID)
	{
		return null;
	}

	public static String getUserGames(int userID) throws SQLException, JSONException
	{
		String sql = "SELECT " + TABLE_Name + ".* FROM " + UsersGamesTable.TABLE_Name + " JOIN "
				+ TABLE_Name + " USING " + "(" + COL_Id + ") WHERE " + UsersGamesTable.TABLE_Name
				+ "." + UsersGamesTable.COL_UserId + "=?;";

		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, userID);
		ResultSet rs = stmt.executeQuery();
		String games = getArrayGamesJSONResultSet(rs);
		rs.close();
		stmt.close();
		return games;
	}

	public static String getTeamTurns(String teamID)
	{
		return null;
	}

	public static String getTeamGames(String teamID)
	{
		return null;
	}

	public static String joinGame(int userId, int gameId) throws SQLException, JSONException
	{
		if (UsersGamesTable.createRow(userId, gameId) == null)
			return null;
		// This get game will be an updated version with the new user
		return getGame(gameId);
	}

	public static int getMaxPlayersForGame(int gameId) throws SQLException, JSONException
	{
		String sql = "SELECT * FROM " + TABLE_Name + " WHERE  " + COL_Id + "=?";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.setInt(1, gameId);
		ResultSet rs = stmt.executeQuery();
		int maxPlayersInGame = 0;
		while (rs.next())
		{
			int numTeams = rs.getInt(COL_NumberTeams);
			int numPlayersPerTeam = rs.getInt(COL_PlayersPerTeams);
			maxPlayersInGame = numTeams * numPlayersPerTeam;
		}
		return maxPlayersInGame;
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

		String usersInGame = UsersTable.getUsersInGame(id);
		return UtilsJSON.getJSON(id, gameState, turn, isPrivate, isRanked, difficulty, numTeams,
				numPlayersPerTeam, timeLimitPerMove, turnStrategy, usersInGame);
	}

	public static boolean gameExists(int gameId) throws SQLException, JSONException
	{
		return getGame(gameId) != null;
	}
}
