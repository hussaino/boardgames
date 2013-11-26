package edu.vt.boardgames.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.Board;
import vt.team9.customgames.Piece;

public class UtilsJSON
{
	/* ** Board class JSON interface ** */
	private static final String JSON_KEY_BOARD_WIDTH = "jk_BOARD_WIDTH";
	private static final String JSON_KEY_BOARD_LENGTH = "jk_BOARD_LENGTH";
	private static final String JSON_KEY_BOARD_PIECES_ARRAY = "jk_BOARD_PIECES_ARRAY";

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
	private static final String JSON_KEY_BOARD_PIECE_NAME = "jk_BOARD_PIECE_NAME";

	public static JSONObject getJSON(Piece piece) throws JSONException
	{
		JSONObject pieceJSON = new JSONObject();
		pieceJSON.put(JSON_KEY_BOARD_PIECE_NAME, piece.getName());
		return pieceJSON;
	}

	public static Piece getPieceFromJSON(JSONObject pieceJSON) throws JSONException
	{
		String name = (String) pieceJSON.get(JSON_KEY_BOARD_PIECE_NAME);
		return new Piece(-1, name);
	}/* ** End Piece JSON interface ** */

}
