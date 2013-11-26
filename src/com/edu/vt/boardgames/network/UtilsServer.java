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

	private static final String URL_SERVLET_EXTENSION_DATA = "/data";
	private static final String URL_BASE = IS_LOCAL_SERVER ? "http://10.0.2.2"
			: "http://ec2-54-234-246-223.compute-1.amazonaws.com";
	private static final String URL_GAMES = URL_BASE + URL_SERVLET_EXTENSION_DATA;

	/*
	 * Currently unrealistic API. Normally to submit a move and get a board from
	 * the server, the user would need a game id. But since I don't assign this,
	 * I just save all submitted boards as a new entry in the DB and return all
	 * boards when getBoadFromServer is called.
	 */
	public static void submitMoveToServer(Handler handler, String keyMsgType, short msgType,
			String keyResponse, Board board)
	{
		try
		{
			JSONObject boardJSON = UtilsJSON.getJSON(board);
			AsyncTaskPostToResource asyncPost = new AsyncTaskPostToResource(handler, keyMsgType,
					msgType, keyResponse, URL_GAMES);

			StringEntity boardEntitiy = new StringEntity(boardJSON.toString());
			asyncPost.execute(boardEntitiy);
		}
		catch (JSONException e)
		{
			MyLogger.logExceptionSevere(UtilsServer.class.getName(), "submitMoveToServer", "", e);
		}
		catch (UnsupportedEncodingException e)
		{
			MyLogger.logExceptionSevere(UtilsServer.class.getName(), "submitMoveToServer", "", e);
		}
	}

	public static void getBoardFromServer(Handler handler, String keyMsgType, short msgType,
			String keyResponse)
	{
		String[] urlsToFetch = { URL_GAMES };

		// Set up content/error handler that will be used during parsing of the
		// rss resource.
		ResourceParserJSON jsonParser = new ResourceParserJSON();

		ControllerHttpGetResource<JSONObject> spiegelHtmlController = new ControllerHttpGetResource<JSONObject>(
				jsonParser, handler, keyMsgType, msgType, keyResponse, urlsToFetch);

		spiegelHtmlController.fetchAndParseResources();

		// Boards gotten from server will be returned to Handler
	}

}
