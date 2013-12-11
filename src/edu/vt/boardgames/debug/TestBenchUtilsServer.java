package edu.vt.boardgames.debug;

import java.util.ArrayList;

import vt.team9.customgames.CustomBoard;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.Team;
import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsServer;
import edu.vt.boardgames.network.response.HandlerResponse;

public class TestBenchUtilsServer {
	private static Context s_context;

	public static void tbInit(final Context context) {
		s_context = context;
	}

	public static void testPostNewGame() {
		User userCreatingGame = new User("doesn't matter");
		userCreatingGame.setId(1);
		UtilsServer.createNewGame(new HandlerResponse<Game>() {
			@Override
			public void onResponseArrayObj(ArrayList<Game> response) {
				if (response != null) {
					Game game = response.get(0);
					CustomBoard board = new CustomBoard(8, 8);
					board.initBoard();
					game.setBoard(board);
					testSubmitNewGameState(game);
					Toast.makeText(s_context, "Post response: " + game,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(s_context, "Received null post response",
							Toast.LENGTH_LONG).show();
				}
			}
		}, true, false, 5, 2, 1, -1, -1, userCreatingGame);
	}

	public static void testGetAllGames() {
		UtilsServer.getAllGamesFromServer(handlerPrintGames);
	}

	public static void testGetGame() {
		UtilsServer.getGameFromServer(handlerPrintGames, 1);
	}

	public static void testGetAllOpenGames() {
		UtilsServer.getAllOpenGames(handlerPrintGames);
	}

	private static HandlerResponse<Game> handlerPrintGames = new HandlerResponse<Game>() {
		@Override
		public void onResponseArrayObj(ArrayList<Game> response) {
			if (response != null) {
				String toast = "";
				for (Game game : response) {
					toast += game.toString();
				}
				Toast.makeText(s_context, "Post response: " + toast,
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(s_context, "Received null post response",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	public static void testSubmitNewGameState(Game game) {
		UtilsServer.submitNewGameState(new HandlerResponse<String>() {
			@Override
			public void onResponseArrayObj(ArrayList<String> response) {
				if (response != null) {
					Toast.makeText(s_context,
							"Put new state response: " + response.get(0),
							Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(s_context,
							"Received null put new game state response",
							Toast.LENGTH_LONG).show();
				}
			}
		}, game);
	}

	public static void testGetAllUsers() {
		UtilsServer.getAllUsers(handlerResponsePrintUser);
	}

	public static void testGetUser(String usr) {
		UtilsServer.getUser(handlerResponsePrintUser, usr);
	}

	public static void testGetUser(int usrId) {
		UtilsServer.getUser(handlerResponsePrintUser, usrId);
	}

	public static void testCreateUser(String username) {
		UtilsServer.createOrLoginUser(handlerResponsePrintUser, username);
	}

	private static HandlerResponse<User> handlerResponsePrintUser = new HandlerResponse<User>() {
		public void onResponseArrayObj(java.util.ArrayList<User> response) {
			if (response != null) {
				String toast = "";
				for (User usr : response) {
					toast += usr + "\n";
				}
				Toast.makeText(s_context, toast, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(s_context,
						"Received null put new game state response",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	public static void testDeleteUser(int userId) {
		UtilsServer.deleteUser(handlerResponseUserDelete, userId);
	}

	public static void testDeleteUser(String username) {
		UtilsServer.deleteUser(handlerResponseUserDelete, username);
	}

	private static HandlerResponse<String> handlerResponseUserDelete = new HandlerResponse<String>() {
		public void onResponseArrayObj(java.util.ArrayList<String> response) {
			if (response != null) {
				Toast.makeText(s_context,
						"Delete response: " + response.get(0),
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(s_context, "Delete response is null.",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	/* Test Team Interface */
	public static void testGetAllTeams() {
		UtilsServer.getAllTeams(handlerResponseTeam);
	}

	public static void testGetTeam(int teamId) {
		UtilsServer.getTeam(handlerResponseTeam, teamId);
	}

	public static void testCreateTeam(String teamName) {
		UtilsServer.createNewTeam(handlerResponseTeam, teamName);
	}

	private static Handler handlerResponseTeam = new HandlerResponse<Team>() {
		@Override
		public void onResponseArrayObj(ArrayList<Team> response) {
			String strResp = "Team response: ";
			if (response != null && response.size() > 0) {
				for (Team t : response) {
					strResp += (t + "\n");
				}
			}
			Toast.makeText(s_context, strResp, Toast.LENGTH_LONG).show();
		}
	};

	public static void testDeleteTeam(int teamId) {
		UtilsServer.deleteTeam(new HandlerResponse<String>() {
			@Override
			public void onResponseArrayObj(ArrayList<String> response) {
				String strResp = "Delete resp: ";
				if (response != null && response.size() > 0) {
					for (String s : response) {
						strResp += (s + "\n");
					}
				}
				Toast.makeText(s_context, strResp, Toast.LENGTH_LONG).show();
			}
		}, teamId);
	}

	public static void testJoinGame(User usr, Game game) {
		UtilsServer.joinGame(new HandlerResponse<Game>() {
			@Override
			public void onResponseArrayObj(ArrayList<Game> response) {
				if (response != null) {
					String toast = "";
					for (Game game : response) {
						toast += game.toString();
					}
					Toast.makeText(s_context, "Post response: " + toast,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(s_context, "Received null post response",
							Toast.LENGTH_LONG).show();
				}
			}

		}, usr, game);
	}

}
