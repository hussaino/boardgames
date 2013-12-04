package edu.vt.boardgames.network;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import vt.team9.customgames.Board;
import android.os.Handler;
import edu.vt.boardgames.debug.MyLogger;
import edu.vt.boardgames.network.async.ControllerHttpRequestAndParse;
import edu.vt.boardgames.network.response.ResponseParserGame;
import edu.vt.boardgames.network.response.ResponseParserString;
import edu.vt.boardgames.network.response.ResponseParserTeam;
import edu.vt.boardgames.network.response.ResponseParserUser;

public class UtilsServer
{
	private static final boolean IS_LOCAL_SERVER = true;

	private static final String URL_BASE = IS_LOCAL_SERVER ? "http://10.0.2.2"
			: "http://ec2-54-234-246-223.compute-1.amazonaws.com";

	private static final String URL_SERVLET_EXTENSION_GAMES = "/games";
	private static final String URL_SERVLET_EXTENSION_JOIN = URL_SERVLET_EXTENSION_GAMES + "/teams";
	private static final String URL_SERVLET_EXTENSION_USERS = "/users";
	private static final String URL_SERVLET_EXTENSION_TEAMS = "/teams";

	private static final String URL_GAMES = URL_BASE + URL_SERVLET_EXTENSION_GAMES;
	private static final String URL_GAMES_JOIN = URL_BASE + URL_SERVLET_EXTENSION_JOIN;
	private static final String URL_USERS = URL_BASE + URL_SERVLET_EXTENSION_USERS;
	private static final String URL_TEAMS = URL_BASE + URL_SERVLET_EXTENSION_TEAMS;

	private static final String URL_PARAM_USER_ID = "user";
	private static final String URL_PARAM_TURN = "turn";
	private static final String URL_PARAM_PRIVATE = "private";
	private static final String URL_PARAM_RANKED = "ranked";
	private static final String URL_PARAM_DIFFICULTY = "difficulty";
	private static final String URL_PARAM_TEAMS = "teams";
	private static final String URL_PARAM_PLAYERS_PER_TEAM = "playersPerTeam";
	private static final String URL_PARAM_TIME_LIMIT = "timeLimit";
	private static final String URL_PARAM_TURN_STRAT = "turnStrat";
	private static final String URL_PARAM_GAMES_OPEN = "open";

	/* Start Games interface */
	public static void createNewGame(Handler handler, Game game)
	{
		createNewGame(handler, game.isPrivate(), game.isRanked(), game.getDifficulty(),
				game.getNumTeams(), game.getNumPlayersPerTeam(), game.getTimeLimitPerMove(),
				game.getTurnStrategy(), game.getPlayers().get(0));
	}

	public static void createNewGame(Handler handler, boolean isPrivate, boolean isRanked,
			int difficulty, int numTeams, int numPlayersPerTeam, int timeLimitPerMove,
			int turnStrategy, User user)
	{
		String urlParams = "?" + formatUrlParam(URL_PARAM_PRIVATE, isPrivate) + "&"
				+ formatUrlParam(URL_PARAM_RANKED, isRanked) + "&"
				+ formatUrlParam(URL_PARAM_DIFFICULTY, difficulty) + "&"
				+ formatUrlParam(URL_PARAM_TEAMS, numTeams) + "&"
				+ formatUrlParam(URL_PARAM_PLAYERS_PER_TEAM, numPlayersPerTeam) + "&"
				+ formatUrlParam(URL_PARAM_TIME_LIMIT, timeLimitPerMove) + "&"
				+ formatUrlParam(URL_PARAM_TURN_STRAT, turnStrategy) + "&"
				+ formatUrlParam(URL_PARAM_USER_ID, user.getId());

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
		getGameUsingUrl(handler, URL_GAMES);
	}

	/*
	 * Board with id gameID will be returned to Handler from server
	 */
	public static void getGameFromServer(Handler handler, int gameId)
	{
		getGameUsingUrl(handler, URL_GAMES + "/" + gameId);
	}

	private static void getGameUsingUrl(Handler handler, String url)
	{
		ResponseParserGame gameParser = new ResponseParserGame();
		HttpGet getRequest = new HttpGet(url);
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
	}/* End Games interface */

	/* Start Users interface */
	public static void getAllUsers(Handler handler)
	{
		getUserWithUrl(handler, URL_USERS);
	}

	public static void getUser(Handler handler, int usrId)
	{
		getUserWithUrl(handler, URL_USERS + "/" + usrId);
	}

	public static void getUser(Handler handler, String usr)
	{
		getUserWithUrl(handler, URL_USERS + "/" + usr);
	}

	private static void getUserWithUrl(Handler handler, String url)
	{
		ResponseParserUser parser = new ResponseParserUser();
		HttpGet getRequest = new HttpGet(url);
		new ControllerHttpRequestAndParse<User>()
				.fetchAndParseRequests(handler, parser, getRequest);
	}

	public static void createOrLoginUser(Handler handler, String username)
	{
		ResponseParserUser parser = new ResponseParserUser();
		HttpPost getRequest = new HttpPost(URL_USERS + "/" + username);
		new ControllerHttpRequestAndParse<User>()
				.fetchAndParseRequests(handler, parser, getRequest);
	}

	public static void deleteUser(Handler handler, int userId)
	{
		deleteUserWithUrl(handler, URL_USERS + "/" + userId);
	}

	public static void deleteUser(Handler handler, String username)
	{
		deleteUserWithUrl(handler, URL_USERS + "/" + username);
	}

	private static void deleteUserWithUrl(Handler handler, String url)
	{
		ResponseParserString parser = new ResponseParserString();
		HttpDelete getRequest = new HttpDelete(url);
		new ControllerHttpRequestAndParse<String>().fetchAndParseRequests(handler, parser,
				getRequest);
	}/* End Users interface */

	/* Start Teams interface */
	public static void getAllTeams(Handler handler)
	{
		getTeamUsingUrl(handler, URL_TEAMS);
	}

	public static void getTeam(Handler handler, int teamId)
	{
		getTeamUsingUrl(handler, URL_TEAMS + "/" + teamId);
	}

	private static void getTeamUsingUrl(Handler handler, String url)
	{
		ResponseParserTeam parser = new ResponseParserTeam();
		HttpGet getRequest = new HttpGet(url);
		new ControllerHttpRequestAndParse<Team>()
				.fetchAndParseRequests(handler, parser, getRequest);
	}

	public static void createNewTeam(Handler handler, String teamName)
	{
		ResponseParserTeam parser = new ResponseParserTeam();
		HttpPost getRequest = new HttpPost(URL_TEAMS + "/" + teamName);
		new ControllerHttpRequestAndParse<Team>()
				.fetchAndParseRequests(handler, parser, getRequest);

	}

	public static void deleteTeam(Handler handler, int teamId)
	{
		ResponseParserString parser = new ResponseParserString();
		HttpDelete getRequest = new HttpDelete(URL_TEAMS + "/" + teamId);
		new ControllerHttpRequestAndParse<String>().fetchAndParseRequests(handler, parser,
				getRequest);
	}/* End Teams interface */

	public static void joinGame(Handler handler, User user, Game game)
	{
		ResponseParserGame parser = new ResponseParserGame();
		HttpPost postNewGameRequest = new HttpPost(URL_GAMES_JOIN);
		new ControllerHttpRequestAndParse<Game>().fetchAndParseRequests(handler, parser,
				postNewGameRequest);
	}

	public static void getAllOpenGames(Handler handler)
	{
		ResponseParserGame parser = new ResponseParserGame();
		HttpGet postNewGameRequest = new HttpGet(URL_GAMES + "?"
				+ formatUrlParam(URL_PARAM_GAMES_OPEN, true));
		new ControllerHttpRequestAndParse<Game>().fetchAndParseRequests(handler, parser,
				postNewGameRequest);
	}
}
