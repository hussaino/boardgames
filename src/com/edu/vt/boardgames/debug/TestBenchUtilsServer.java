package edu.vt.boardgames.debug;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.Board;
import vt.team9.customgames.ChessBoard;
import vt.team9.customgames.Game;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import edu.vt.boardgames.network.UtilsJSON;
import edu.vt.boardgames.network.UtilsServer;

public class TestBenchUtilsServer
{
	private static final String KEY_RESPONSE = "response_key";
	private static final String KEY_MSG_TYPE = "msg_Type";
	private static final short MSG_TYPE_RESPONSE_POST_NEW_GAME = 1;
	private static final short MSG_TYPE_RESPONSE_GET_ALL_GAMES = 2;
	private static final short MSG_TYPE_RESPONSE_SUBMIT_GAME_STATE = 3;

	private static Context s_context;

	public static void tbInit(final Context context)
	{
		s_context = context;
	}

	public static void testPostNewGame()
	{
		UtilsServer.createNewGame(s_handler, KEY_MSG_TYPE, MSG_TYPE_RESPONSE_POST_NEW_GAME,
				KEY_RESPONSE, true, false, 5, 2, 1, -1, -1);
	}

	public static void testGetBoards()
	{
		UtilsServer.getGamesFromServer(s_handler, KEY_MSG_TYPE, MSG_TYPE_RESPONSE_GET_ALL_GAMES,
				KEY_RESPONSE);
	}

	public static void testSubmitNewGameState(Game game)
	{
		UtilsServer.submitNewGameState(s_handler, KEY_MSG_TYPE,
				MSG_TYPE_RESPONSE_SUBMIT_GAME_STATE, KEY_RESPONSE, game);
	}

	private static Handler s_handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			Bundle msgBundle = msg.getData();

			switch (msgBundle.getShort(KEY_MSG_TYPE))
			{
			case MSG_TYPE_RESPONSE_POST_NEW_GAME:
				String postResponse = msgBundle.getString(KEY_RESPONSE);
				if (postResponse != null)
				{
					try
					{
						Game game = UtilsJSON.getGameFromJSON(new JSONObject(postResponse));
						ChessBoard board = new ChessBoard(8, 8);
						board.initBoard();
						game.setGameState(UtilsJSON.getJSON(board).toString());
						testSubmitNewGameState(game);
						Toast.makeText(s_context, "Post response: " + game, Toast.LENGTH_LONG)
								.show();
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case MSG_TYPE_RESPONSE_GET_ALL_GAMES:
				ArrayList<Game> getResponseGames = (ArrayList<Game>) msgBundle
						.getSerializable(KEY_RESPONSE);
				if (getResponseGames != null)
				{
					String toast = "";
					for (Game game : getResponseGames)
					{
						toast += game.toString();
					}
					Toast.makeText(s_context, "Post response: " + toast, Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case MSG_TYPE_RESPONSE_SUBMIT_GAME_STATE:
				String putNewStateResponse = msgBundle.getString(KEY_RESPONSE);
				if (putNewStateResponse != null)
				{
					Toast.makeText(s_context, "Put new state response: " + putNewStateResponse,
							Toast.LENGTH_LONG).show();

				}
				else
				{
					Toast.makeText(s_context, "Received null put new game state response",
							Toast.LENGTH_LONG).show();
				}
				break;
			default:
				MyLogger.logWarning(TestBenchUtilsServer.class.getName(),
						"UI_Handler handleMessage",
						"MainActivity Handler received non-supported message type");
				break;
			}
		}
	};
}
