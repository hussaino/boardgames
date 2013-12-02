package edu.vt.boardgames.debug;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.Board;
import vt.team9.customgames.ChessBoard;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsJSON;
import edu.vt.boardgames.network.UtilsServer;

public class TestBenchUtilsServer
{
	private static Context s_context;
	private static String KEY_RESPONSE = "response";

	public static void tbInit(final Context context)
	{
		s_context = context;
	}

	public static void testPostNewGame()
	{
		UtilsServer.createNewGame(new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);

				Bundle msgBundle = msg.getData();

				ArrayList<Game> getResponseGames = (ArrayList<Game>) msgBundle
						.getSerializable(KEY_RESPONSE);
				if (getResponseGames != null)
				{
					Game game = getResponseGames.get(0);
					ChessBoard board = new ChessBoard(8, 8);
					board.initBoard();
					game.setBoard(board);
					testSubmitNewGameState(game);
					Toast.makeText(s_context, "Post response: " + game, Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}
			}
		}, true, false, 5, 2, 1, -1, -1);
	}

	public static void testGetAllBoards()
	{
		UtilsServer.getAllGamesFromServer(new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);

				Bundle msgBundle = msg.getData();
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

			}
		});
	}

	public static void testGetBoard()
	{
		UtilsServer.getGameFromServer(new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);

				Bundle msgBundle = msg.getData();
				ArrayList<Game> getResponseGames = (ArrayList<Game>) msgBundle
						.getSerializable(KEY_RESPONSE);
				if (getResponseGames != null)
				{
					Game gameFetched = getResponseGames.get(0);
					Toast.makeText(s_context, "Post response: " + gameFetched, Toast.LENGTH_LONG)
							.show();
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}

			}
		}, 1);
	}

	public static void testSubmitNewGameState(Game game)
	{
		UtilsServer.submitNewGameState(new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);

				Bundle msgBundle = msg.getData();
				ArrayList<String> putNewStateResponse = (ArrayList<String>) msgBundle
						.getSerializable(KEY_RESPONSE);
				if (putNewStateResponse != null)
				{
					Toast.makeText(s_context,
							"Put new state response: " + putNewStateResponse.get(0),
							Toast.LENGTH_LONG).show();

				}
				else
				{
					Toast.makeText(s_context, "Received null put new game state response",
							Toast.LENGTH_LONG).show();
				}
			}
		}, game);
	}

	public static void testGetAllUsers()
	{
		UtilsServer.getAllUsers(handlerResponseUser);
	}

	public static void testGetUser(String usr)
	{
		UtilsServer.getUser(handlerResponseUser, usr);
	}

	public static void testGetUser(int usrId)
	{
		UtilsServer.getUser(handlerResponseUser, usrId);
	}

	public static void testCreateUser(String username)
	{
		UtilsServer.createNewUser(handlerResponseUser, username);
	}

	private static Handler handlerResponseUser = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			Bundle msgBundle = msg.getData();
			ArrayList<User> allUsers = (ArrayList<User>) msgBundle.getSerializable(KEY_RESPONSE);
			if (allUsers != null)
			{
				String toast = "";
				for (User usr : allUsers)
				{
					toast += usr + "\n";
				}
				Toast.makeText(s_context, toast, Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(s_context, "Received null put new game state response",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	public static void testDeleteUser(int userId)
	{
		UtilsServer.deleteUser(handlerResponseUserDelete, userId);
	}

	public static void testDeleteUser(String username)
	{
		UtilsServer.deleteUser(handlerResponseUserDelete, username);
	}

	private static Handler handlerResponseUserDelete = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			Bundle msgBundle = msg.getData();
			ArrayList<String> putNewStateResponse = (ArrayList<String>) msgBundle
					.getSerializable(KEY_RESPONSE);
			if (putNewStateResponse != null)
			{
				Toast.makeText(s_context, "Delete response: " + putNewStateResponse.get(0),
						Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(s_context, "Delete response is null.", Toast.LENGTH_LONG).show();
			}
		}
	};

}
