package com.boarge.server.utils;

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
	private static final String JSON_KEY_GAME_USERS = "k_usrs";

	public static JSONObject getJSON(Integer id, String gameState, Integer turn, Boolean isPrivate,
			Boolean isRanked, Integer difficulty, Integer numTeams, Integer numPlayersPerTeam,
			Integer timeLimitPerMove, Integer turnStrategy, String usersArray) throws JSONException
	{
		JSONObject gameJSON = new JSONObject();
		gameJSON.put(JSON_KEY_GAME_ID, id);
		gameJSON.put(JSON_KEY_GAME_STATE, gameState);
		gameJSON.put(JSON_KEY_GAME_TURN, turn);
		gameJSON.put(JSON_KEY_GAME_PRIVATE, isPrivate);
		gameJSON.put(JSON_KEY_GAME_RANKED, isRanked);
		gameJSON.put(JSON_KEY_GAME_DIFFICULTY, difficulty);
		gameJSON.put(JSON_KEY_GAME_NUM_TEAMS, numTeams);
		gameJSON.put(JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM, numPlayersPerTeam);
		gameJSON.put(JSON_KEY_GAME_TIME_LIMIT, timeLimitPerMove);
		gameJSON.put(JSON_KEY_GAME_TURN_STRATEGY, turnStrategy);
		gameJSON.put(JSON_KEY_GAME_USERS, usersArray);
		return gameJSON;
	}/* ** end Game class JSON interface ** */

	/* ** User class JSON interface ** */
	private static final String JSON_KEY_USER_ID = "k_id";
	private static final String JSON_KEY_USER_NAME = "k_nm";

	public static JSONObject getJSON(int id, String userName) throws JSONException
	{
		JSONObject gameJSON = new JSONObject();
		gameJSON.put(JSON_KEY_USER_ID, id);
		gameJSON.put(JSON_KEY_USER_NAME, userName);
		return gameJSON;
	} /* ** End User class JSON interface ** */

	/* ** Team class JSON interface ** */
	private static final String JSON_KEY_TEAM_ID = "k_id";
	private static final String JSON_KEY_TEAM_NAME = "k_nm";

	public static JSONObject getTeamJSON(int id, String teamName) throws JSONException
	{
		JSONObject gameJSON = new JSONObject();
		gameJSON.put(JSON_KEY_TEAM_ID, id);
		gameJSON.put(JSON_KEY_TEAM_NAME, teamName);
		return gameJSON;
	}/* ** End Team class JSON interface ** */

	/**
	 * 
	 * @param str
	 *            - to try converting to json
	 * @return - true if string is valid json object and it is not empty. Will
	 *         throw an exception if not valid json.
	 * @throws JSONException
	 */
	public static boolean isValidJSON(String str) throws JSONException
	{
		JSONObject jsonMapEntities = new JSONObject(str);
		return jsonMapEntities.length() > 0;
	}
}