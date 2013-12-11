package edu.vt.boardgames.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.Piece;
import vt.team9.customgames.Board;

@SuppressWarnings("all")
public class UtilsJSON {
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

	public static JSONObject getJSON(Game game) throws JSONException {
		JSONObject gameJSON = new JSONObject();
		gameJSON.put(JSON_KEY_GAME_ID, game.getId());
		gameJSON.put(JSON_KEY_GAME_STATE, UtilsJSON.getJSON(game.getBoard()));
		gameJSON.put(JSON_KEY_GAME_TURN, game.getTurn());
		gameJSON.put(JSON_KEY_GAME_PRIVATE, game.isPrivate());
		gameJSON.put(JSON_KEY_GAME_RANKED, game.isRanked());
		gameJSON.put(JSON_KEY_GAME_DIFFICULTY, game.getDifficulty());
		gameJSON.put(JSON_KEY_GAME_NUM_TEAMS, game.getNumTeams());
		gameJSON.put(JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM,
				game.getNumPlayersPerTeam());
		gameJSON.put(JSON_KEY_GAME_TIME_LIMIT, game.getTimeLimitPerMove());
		gameJSON.put(JSON_KEY_GAME_TURN_STRATEGY, game.getTurnStrategy());

		// Add list of users
		JSONArray arrayUsersInGame = new JSONArray();
		for (User usr : game.getPlayers()) {
			arrayUsersInGame.put(UtilsJSON.getJSON(usr));
		}
		gameJSON.put(JSON_KEY_GAME_USERS, arrayUsersInGame);
		return gameJSON;
	}

	public static Game getGameFromJSON(JSONObject gameJSON)
			throws JSONException {
		boolean isPrivate = gameJSON.getBoolean(JSON_KEY_GAME_PRIVATE);
		boolean isRanked = gameJSON.getBoolean(JSON_KEY_GAME_RANKED);
		int difficulty = gameJSON.getInt(JSON_KEY_GAME_DIFFICULTY);
		int numTeams = gameJSON.getInt(JSON_KEY_GAME_NUM_TEAMS);
		int numPlayersPerTeam = gameJSON
				.getInt(JSON_KEY_GAME_NUM_PLAYERS_PER_TEAM);
		int timeLimitPerMove = gameJSON.getInt(JSON_KEY_GAME_TIME_LIMIT);
		int turnStrategy = gameJSON.getInt(JSON_KEY_GAME_TURN_STRATEGY);
		ArrayList<User> players = new ArrayList<User>();
		JSONArray arrayUsersInGame = new JSONArray(
				gameJSON.getString(JSON_KEY_GAME_USERS));
		for (int i = 0; i < arrayUsersInGame.length(); i++) {
			User usr = UtilsJSON.getUserFromJSON(arrayUsersInGame
					.getJSONObject(i));
			players.add(usr);
		}

		Game game = new Game(isPrivate, isRanked, difficulty, numTeams,
				numPlayersPerTeam, timeLimitPerMove, turnStrategy);

		game.setPlayers(players);

		// The following parts of a game object may or may not be mapped to
		// already.
		try {
			JSONObject boardJSON = new JSONObject(
					gameJSON.getString(JSON_KEY_GAME_STATE));
			game.setBoard(UtilsJSON.getBoardFromJSON(boardJSON));
		} catch (JSONException e) {

		}

		try {
			game.setId(gameJSON.getInt(JSON_KEY_GAME_ID));
		} catch (JSONException e) {

		}

		try {
			game.setTurn(gameJSON.getInt(JSON_KEY_GAME_TURN));
		} catch (JSONException e) {

		}

		return game;
	}/* ** End Game JSON interface ** */

	/* ** Board class JSON interface ** */
	private static final String JSON_KEY_BOARD_WIDTH = "k_w";
	private static final String JSON_KEY_BOARD_LENGTH = "k_l";
	private static final String JSON_KEY_BOARD_PIECES_ARRAY = "k_arr";
	private static final String JSON_KEY_BOARD_CLASS = "k_cls";

	public static JSONObject getJSON(Board board) throws JSONException {
		JSONObject boardJSON = new JSONObject();

		int width = board.width_;
		int length = board.length_;

		boardJSON.put(JSON_KEY_BOARD_WIDTH, width);
		boardJSON.put(JSON_KEY_BOARD_LENGTH, length);
		boardJSON.put(JSON_KEY_BOARD_CLASS, board.getClass().getName());

		JSONArray piecesArrayJSON = new JSONArray();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				piecesArrayJSON.put(getJSON(board.Pieces_[i][j]));
			}
		}
		boardJSON.put(JSON_KEY_BOARD_PIECES_ARRAY, piecesArrayJSON);
		return boardJSON;
	}

	public static Board getBoardFromJSON(JSONObject boardJSON)
			throws JSONException {
		Board board = null;
		int width = (int) boardJSON.getInt(JSON_KEY_BOARD_WIDTH);
		int length = (int) boardJSON.getInt(JSON_KEY_BOARD_LENGTH);
		String objClassName = boardJSON.getString(JSON_KEY_BOARD_CLASS);
		try {
			Class<Board> objClass = (Class<Board>) Class.forName(objClassName);
			board = objClass.newInstance();
			board.width_ = width;
			board.length_ = length;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Piece[][] boardPieces = new Piece[width][length];
		JSONArray piecesArrayJSON = (JSONArray) boardJSON
				.get(JSON_KEY_BOARD_PIECES_ARRAY);
		for (int i = 0; i < piecesArrayJSON.length(); i++) {
			JSONObject pieceJSON = piecesArrayJSON.getJSONObject(i);
			boardPieces[i / width][i % width] = getPieceFromJSON(pieceJSON);
		}
		board.Pieces_ = boardPieces;
		return board;
	}/* ** End Board JSON interface ** */

	/* ** Piece JSON interface ** */
	private static final String JSON_KEY_BOARD_PIECE_NAME = "k_nm";
	private static final String JSON_KEY_BOARD_PIECE_TEAM = "k_tm";
	private static final String JSON_KEY_BOARD_PIECE_CLASS = "k_cls";

	public static JSONObject getJSON(Piece piece) throws JSONException {
		JSONObject pieceJSON = new JSONObject();
		pieceJSON.put(JSON_KEY_BOARD_PIECE_NAME, piece.getName());
		pieceJSON.put(JSON_KEY_BOARD_PIECE_TEAM, piece.getTeam_());
		pieceJSON.put(JSON_KEY_BOARD_PIECE_CLASS, piece.getClass().getName());
		return pieceJSON;
	}

	public static Piece getPieceFromJSON(JSONObject pieceJSON)
			throws JSONException {
		Piece piece = null;
		String name = pieceJSON.getString(JSON_KEY_BOARD_PIECE_NAME);
		int team = pieceJSON.getInt(JSON_KEY_BOARD_PIECE_TEAM);
		String objClassName = pieceJSON.getString(JSON_KEY_BOARD_PIECE_CLASS);
		try {
			Class<Piece> objClass = (Class<Piece>) Class.forName(objClassName);
			piece = (Piece) objClass.newInstance();
			piece.setTeam_(team);
			piece.setName(name);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return piece;
	}/* ** End Piece JSON interface ** */

	/* ** User JSON interface ** */
	private static final String JSON_KEY_USER_ID = "k_id";
	private static final String JSON_KEY_USER_NAME = "k_nm";

	public static JSONObject getJSON(User user) throws JSONException {
		JSONObject userJSON = new JSONObject();
		userJSON.put(JSON_KEY_USER_ID, user.getId());
		userJSON.put(JSON_KEY_USER_NAME, user.getName());
		return userJSON;
	}

	public static User getUserFromJSON(JSONObject userJSON)
			throws JSONException {
		Integer id = userJSON.getInt(JSON_KEY_USER_ID);
		String name = userJSON.getString(JSON_KEY_USER_NAME);

		User user = new User(name);
		user.setId(id);

		return user;
	}/* ** End User JSON interface ** */

	/* ** Team JSON interface ** */
	private static final String JSON_KEY_TEAM_ID = "k_id";
	private static final String JSON_KEY_TEAM_NAME = "k_nm";

	public static JSONObject getJSON(Team team) throws JSONException {
		JSONObject teamJSON = new JSONObject();
		teamJSON.put(JSON_KEY_TEAM_ID, team.getId());
		teamJSON.put(JSON_KEY_TEAM_NAME, team.getName());
		return teamJSON;
	}

	public static Team getTeamFromJSON(JSONObject teamJSON)
			throws JSONException {
		Integer id = teamJSON.getInt(JSON_KEY_TEAM_ID);
		String name = teamJSON.getString(JSON_KEY_TEAM_NAME);

		Team team = new Team(name);
		team.setId(id);

		return team;
	}/* ** End Team JSON interface ** */
}
