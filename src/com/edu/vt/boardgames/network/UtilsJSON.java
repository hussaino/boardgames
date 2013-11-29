package edu.vt.boardgames.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.Board;
import vt.team9.customgames.Game;
import vt.team9.customgames.Piece;

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

	/* ** Board class JSON interface ** */
	private static final String JSON_KEY_BOARD_WIDTH = "k_w";
	private static final String JSON_KEY_BOARD_LENGTH = "k_l";
	private static final String JSON_KEY_BOARD_PIECES_ARRAY = "k_arr";

	public static JSONObject getJSON(Board board) throws JSONException
	{
		JSONObject boardJSON = new JSONObject();

		int width = board.getWidth();
		int length = board.getLength();

		boardJSON.put(JSON_KEY_BOARD_WIDTH, width);
		boardJSON.put(JSON_KEY_BOARD_LENGTH, length);

		JSONArray piecesArrayJSON = new JSONArray();
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < length; j++)
			{
				piecesArrayJSON.put(getJSON(board.getPieces()[i][j]));
			}
		}
		boardJSON.put(JSON_KEY_BOARD_PIECES_ARRAY, piecesArrayJSON);
		return boardJSON;
	}

	public static Board getBoardFromJSON(JSONObject boardJSON) throws JSONException
	{
		int width = (int) boardJSON.getInt(JSON_KEY_BOARD_WIDTH);
		int length = (int) boardJSON.getInt(JSON_KEY_BOARD_LENGTH);
		Board board = new Board(length, width);

		Piece[][] boardPieces = board.getPieces();
		JSONArray piecesArrayJSON = (JSONArray) boardJSON.get(JSON_KEY_BOARD_PIECES_ARRAY);
		for (int i = 0; i < piecesArrayJSON.length(); i++)
		{
			JSONObject pieceJSON = piecesArrayJSON.getJSONObject(i);
			boardPieces[i / width][i % width] = getPieceFromJSON(pieceJSON);
		}
		return board;
	}/* ** End Board JSON interface ** */

	/* ** Piece JSON interface ** */
	private static final String JSON_KEY_BOARD_PIECE_NAME = "k_pce";
	private static final String JSON_KEY_BOARD_PIECE_TEAM = "k_tm";

	public static JSONObject getJSON(Piece piece) throws JSONException
	{
		JSONObject pieceJSON = new JSONObject();
		pieceJSON.put(JSON_KEY_BOARD_PIECE_NAME, piece.getName());
		pieceJSON.put(JSON_KEY_BOARD_PIECE_TEAM, piece.getTeam_());
		return pieceJSON;
	}

	public static Piece getPieceFromJSON(JSONObject pieceJSON) throws JSONException
	{
		String name = pieceJSON.getString(JSON_KEY_BOARD_PIECE_NAME);
		int team = pieceJSON.getInt(JSON_KEY_BOARD_PIECE_TEAM);
		return new Piece(team, name);
	}/* ** End Piece JSON interface ** */

}
