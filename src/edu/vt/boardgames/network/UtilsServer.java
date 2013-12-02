package edu.vt.boardgames.network;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import vt.team9.customgames.Board;
import android.os.Handler;
import edu.vt.boardgames.debug.MyLogger;

public class UtilsServer
{
	private static final boolean IS_LOCAL_SERVER = true;

	private static final String URL_BASE = IS_LOCAL_SERVER ? "http://10.0.2.2"
			: "http://ec2-54-234-246-223.compute-1.amazonaws.com";

	private static final String URL_SERVLET_EXTENSION_GAMES = "/games";
	private static final String URL_SERVLET_EXTENSION_USERS = "/users";
	private static final String URL_GAMES = URL_BASE + URL_SERVLET_EXTENSION_GAMES;
	private static final String URL_USERS = URL_BASE + URL_SERVLET_EXTENSION_USERS;

	private static final String URL_PARAM_USER_ID = "user";
	private static final String URL_PARAM_TURN = "turn";
	private static final String URL_PARAM_PRIVATE = "private";
	private static final String URL_PARAM_RANKED = "ranked";
	private static final String URL_PARAM_DIFFICULTY = "difficulty";
	private static final String URL_PARAM_TEAMS = "teams";
	private static final String URL_PARAM_PLAYERS_PER_TEAM = "playersPerTeam";
	private static final String URL_PARAM_TIME_LIMIT = "timeLimit";
	private static final String URL_PARAM_TURN_STRAT = "turnStrat";

	public static void createNewGame(Handler handler, Game game)
	{
		createNewGame(handler, game.isPrivate(), game.isRanked(), game.getDifficulty(),
				game.getNumTeams(), game.getNumPlayersPerTeam(), game.getTimeLimitPerMove(),
				game.getTurnStrategy());
	}

	public static void createNewGame(Handler handler, boolean isPrivate, boolean isRanked,
			int difficulty, int numTeams, int numPlayersPerTeam, int timeLimitPerMove,
			int turnStrategy)
	{
		String urlParams = "?" + formatUrlParam(URL_PARAM_PRIVATE, isPrivate) + "&"
				+ formatUrlParam(URL_PARAM_RANKED, isRanked) + "&"
				+ formatUrlParam(URL_PARAM_DIFFICULTY, difficulty) + "&"
				+ formatUrlParam(URL_PARAM_TEAMS, numTeams) + "&"
				+ formatUrlParam(URL_PARAM_PLAYERS_PER_TEAM, numPlayersPerTeam) + "&"
				+ formatUrlParam(URL_PARAM_TIME_LIMIT, timeLimitPerMove) + "&"
				+ formatUrlParam(URL_PARAM_TURN_STRAT, turnStrategy);

		ResponseParserGame parser = new ResponseParserGame();
		HttpPost postNewGameRequest = new HttpPost(URL_GAMES + urlParams);
		// Boards gotten from server will be returned to Handler
		new ControllerHttpRequestAndParse<Game>().fetchAndParseRequests(handler, parser,
				postNewGameRequest);
	}

	public static void submitNewGameState(Handler handler, Game updatedGame)
	{
		try
		{
			// Set up Put request
			String url = URL_GAMES + "/" + updatedGame.getId() + "?"
					+ formatUrlParam(URL_PARAM_USER_ID, 1) + "&"
					+ formatUrlParam(URL_PARAM_TURN, updatedGame.getTurn());

			HttpPut putNewGameStateRequest = new HttpPut(url);

			Board board = updatedGame.getBoard();
			StringEntity boardEntity = new StringEntity(UtilsJSON.getJSON(board).toString());
			putNewGameStateRequest.setEntity(boardEntity);
			putNewGameStateRequest.setHeader("Accept", "application/json");
			putNewGameStateRequest.setHeader("Content-type", "application/json");

			ResponseParserString parser = new ResponseParserString();
			new ControllerHttpRequestAndParse<String>().fetchAndParseRequests(handler, parser,
					putNewGameStateRequest);
		}
		catch (UnsupportedEncodingException e)
		{
			MyLogger.logExceptionSevere(UtilsServer.class.getName(), "submitMoveToServer", "", e);
		}
		catch (JSONException e)
		{
			MyLogger.logExceptionSevere(UtilsServer.class.getName(), "submitMoveToServer", "", e);
		}
	}

	/*
	 * Boards gotten from server will be returned to Handler
	 */
	public static void getAllGamesFromServer(Handler handler)
	{
		ResponseParserGame parser = new ResponseParserGame();
		HttpGet getRequest = new HttpGet(URL_GAMES);
		new ControllerHttpRequestAndParse<Game>()
				.fetchAndParseRequests(handler, parser, getRequest);
	}

	/*
	 * Board with id gameID will be returned to Handler from server
	 */
	public static void getGameFromServer(Handler handler, int gameId)
	{
		ResponseParserGame gameParser = new ResponseParserGame();
		HttpGet getRequest = new HttpGet(URL_GAMES + "/" + gameId);
		new ControllerHttpRequestAndParse<Game>().fetchAndParseRequests(handler, gameParser,
				getRequest);
	}

	private static String formatUrlParam(String paramName, int val)
	{
		return paramName + "=" + String.valueOf(val);
	}

	private static String formatUrlParam(String paramName, boolean val)
	{
		return paramName + "=" + String.valueOf(val);
	}

	public static void getAllUsers(Handler handler)
	{
		ResponseParserUser parser = new ResponseParserUser();
		HttpGet getRequest = new HttpGet(URL_USERS);
		new ControllerHttpRequestAndParse<User>()
				.fetchAndParseRequests(handler, parser, getRequest);
	}

}
