package com.boarge.server.temp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilsJSON
{
	/* ** Game class JSON interface ** */
	private static final String JSON_KEY_GAME_ID = "k_id";
	private static final String JSON_KEY_GAME_STATE = "k_state";
	private static final String JSON_KEY_GAME_TURN = "k_trn";
	private static final String JSON_KEY_GAME_PRIVATE = "k_prvt";
	private static final String JSON_KEY_GAME_RANKED = "k_rnkd";
	private static final String JSON_KEY_GAME_DIFFICULTY = "k_diff";
	private static final String JSON_KEY_GAME_NUM_TEAMS = "k_numT";
	private static final String JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM = "k_numP";
	private static final String JSON_KEY_GAME_TIME_LIMIT = "k_time";
	private static final String JSON_KEY_GAME_TURN_STRATEGY = "k_trnStr";

	public static JSONObject getJSON(Game game) throws JSONException
	{
		JSONObject gameJSON = new JSONObject();
		gameJSON.put(JSON_KEY_GAME_ID, game.getId());
		gameJSON.put(JSON_KEY_GAME_STATE, game.getGameState());
		gameJSON.put(JSON_KEY_GAME_TURN, game.getTurn());
		gameJSON.put(JSON_KEY_GAME_PRIVATE, game.isPrivate());
		gameJSON.put(JSON_KEY_GAME_RANKED, game.isRanked());
		gameJSON.put(JSON_KEY_GAME_DIFFICULTY, game.getDifficulty());
		gameJSON.put(JSON_KEY_GAME_NUM_TEAMS, game.getNumTeams());
		gameJSON.put(JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM, game.getNumPlayersPerTeam());
		gameJSON.put(JSON_KEY_GAME_TIME_LIMIT, game.getTimeLimitPerMove());
		gameJSON.put(JSON_KEY_GAME_TURN_STRATEGY, game.getTurnStrategy());
		return gameJSON;
	}

	public static Game getGameFromJSON(JSONObject gameJSON) throws JSONException
	{
		boolean isPrivate = gameJSON.getBoolean(JSON_KEY_GAME_PRIVATE);
		boolean isRanked = gameJSON.getBoolean(JSON_KEY_GAME_RANKED);
		int difficulty = gameJSON.getInt(JSON_KEY_GAME_DIFFICULTY);
		int numTeams = gameJSON.getInt(JSON_KEY_GAME_NUM_TEAMS);
		int numPlayersPerTeam = gameJSON.getInt(JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM);
		int timeLimitPerMove = gameJSON.getInt(JSON_KEY_GAME_TIME_LIMIT);
		int turnStrategy = gameJSON.getInt(JSON_KEY_GAME_TURN_STRATEGY);
		Game game = new Game(isPrivate, isRanked, difficulty, numTeams, numPlayersPerTeam,
				timeLimitPerMove, turnStrategy);

		game.setGameState(gameJSON.getString(JSON_KEY_GAME_STATE));
		game.setId(gameJSON.getInt(JSON_KEY_GAME_ID));
		game.setTurn(gameJSON.getInt(JSON_KEY_GAME_TURN));

		return game;
	}/* ** End Game JSON interface ** */

}
