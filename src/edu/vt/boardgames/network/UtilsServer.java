package edu.vt.boardgames.network;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.Board;
import android.os.Handler;
import edu.vt.boardgames.debug.MyLogger;

public class UtilsServer
{
	private static final boolean IS_LOCAL_SERVER = true;

	private static final String URL_SERVLET_EXTENSION_GAMES = "/games";
	private static final String URL_BASE = IS_LOCAL_SERVER ? "http://10.0.2.2"
			: "http://ec2-54-234-246-223.compute-1.amazonaws.com";
	private static final String URL_GAMES = URL_BASE + URL_SERVLET_EXTENSION_GAMES;

	private static final String URL_PARAM_USER_ID = "user";
	private static final String URL_PARAM_PRIVATE = "private";
	private static final String URL_PARAM_RANKED = "ranked";
	private static final String URL_PARAM_DIFFICULTY = "difficulty";
	private static final String URL_PARAM_TEAMS = "teams";
	private static final String URL_PARAM_PLAYERS_PER_TEAM = "playersPerTeam";
	private static final String URL_PARAM_TIME_LIMIT = "timeLimit";
	private static final String URL_PARAM_TURN_STRAT = "turnStrat";

	public static void createNewGame(Handler handler, String keyMsgType, short msgType,
			String keyResponse, Game game)
	{
		createNewGame(handler, keyMsgType, msgType, keyResponse, game.isPrivate(), game.isRanked(),
				game.getDifficulty(), game.getNumTeams(), game.getNumPlayersPerTeam(),
				game.getTimeLimitPerMove(), game.getTurnStrategy());
	}

	public static void createNewGame(Handler handler, String keyMsgType, short msgType,
			String keyResponse, boolean isPrivate, boolean isRanked, int difficulty, int numTeams,
			int numPlayersPerTeam, int timeLimitPerMove, int turnStrategy)
	{
		String urlParams = "?" + formatUrlParam(URL_PARAM_PRIVATE, isPrivate) + "&"
				+ formatUrlParam(URL_PARAM_RANKED, isRanked) + "&"
				+ formatUrlParam(URL_PARAM_DIFFICULTY, difficulty) + "&"
				+ formatUrlParam(URL_PARAM_TEAMS, numTeams) + "&"
				+ formatUrlParam(URL_PARAM_PLAYERS_PER_TEAM, numPlayersPerTeam) + "&"
				+ formatUrlParam(URL_PARAM_TIME_LIMIT, timeLimitPerMove) + "&"
				+ formatUrlParam(URL_PARAM_TURN_STRAT, turnStrategy);
		AsyncTaskPostPutRequest asyncPost = new AsyncTaskPostPutRequest(handler, keyMsgType,
				msgType, keyResponse, URL_GAMES + urlParams, true);

		asyncPost.execute();

	}

	private static String formatUrlParam(String paramName, int val)
	{
		return paramName + "=" + String.valueOf(val);
	}

	private static String formatUrlParam(String paramName, boolean val)
	{
		return paramName + "=" + String.valueOf(val);
	}

	/*
	 * Currently unrealistic API. Normally to submit a move and get a board from
	 * the server, the user would need a game id. But since I don't assign this,
	 * I just save all submitted boards as a new entry in the DB and return all
	 * boards when getBoadFromServer is called.
	 */
	public static void submitNewGameState(Handler handler, String keyMsgType, short msgType,
			String keyResponse, Game updatedGame)
	{
		try
		{
			int userId = 1;
			String urlParams = URL_GAMES + "/" + updatedGame.getId() + "?"
					+ formatUrlParam(URL_PARAM_USER_ID, userId);
			AsyncTaskPostPutRequest asyncPost = new AsyncTaskPostPutRequest(handler, keyMsgType,
					msgType, keyResponse, urlParams, false);
			StringEntity boardEntity = new StringEntity(updatedGame.getGameState());
			asyncPost.execute(boardEntity);
		}
		catch (UnsupportedEncodingException e)
		{
			MyLogger.logExceptionSevere(UtilsServer.class.getName(), "submitMoveToServer", "", e);
		}
	}

	public static void getGamesFromServer(Handler handler, String keyMsgType, short msgType,
			String keyResponse)
	{
		String[] urlsToFetch = { URL_GAMES };

		// Set up content/error handler that will be used during parsing of the
		// rss resource.
		// ResourceParserJSON jsonParser = new ResourceParserJSON();
		ResourceParserGame gameParser = new ResourceParserGame();

		ControllerHttpGetResource<Game> spiegelHtmlController = new ControllerHttpGetResource<Game>(
				gameParser, handler, keyMsgType, msgType, keyResponse, urlsToFetch);

		spiegelHtmlController.fetchAndParseResources();

		// Boards gotten from server will be returned to Handler
	}

}