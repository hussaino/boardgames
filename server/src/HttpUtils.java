package com.boarge.server;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.boarge.server.temp.Board;
import com.boarge.server.temp.Piece;

public class HttpUtils
{
	private static final String JSON_KEY_BOARD_WIDTH = "JSON_KEY_BOARD_WIDTH";
	private static final String JSON_KEY_BOARD_LENGTH = "JSON_KEY_BOARD_LENGTH";
	private static final String JSON_KEY_BOARD_PIECES_ARRAY = "JSON_KEY_BOARD_PIECES_ARRAY";
	private static final Object JSON_KEY_BOARD_PIECE_NAME = "JSON_KEY_BOARD_PIECE_NAME";

	/*
	 * Currently unrealistic API. Normally to submit a move and get a board from
	 * the server, the user would need a game id. But since I don't assign this,
	 * I just save all submitted boards as a new entry in the DB and return all
	 * boards when getBoadFromServer is called.
	 */
	public static JSONObject submitMoveToServer(Board board)
	{
		JSONObject boardJSON = getBoardJSON(board);
		System.out.println("Ready to submit:\n\t" + boardJSON);
		return boardJSON;
		// Start async task and pass in boardJSON to be posted to server
	}

	// public static Board getBoardFromServer(String strJsonBoard) throws
	// ParseException
	// {
	// // Start async task to fetch json object from server
	// JSONParser parser = new JSONParser();
	// JSONObject boardJSON = (JSONObject) parser.parse(strJsonBoard);
	// return parseJsonToBoard(boardJSON);
	// }

	private static JSONObject getBoardJSON(Board board)
	{
		JSONObject boardJSON = new JSONObject();
		boardJSON.put(JSON_KEY_BOARD_WIDTH, board.width_);
		boardJSON.put(JSON_KEY_BOARD_LENGTH, board.length_);

		JSONArray piecesArrayJSON = new JSONArray();
		for (int i = 0; i < board.width_; i++)
		{
			for (int j = 0; j < board.length_; j++)
			{
				piecesArrayJSON.add(getPieceJSON(board.Pieces_[i][j]));
			}
		}
		boardJSON.put(JSON_KEY_BOARD_PIECES_ARRAY, piecesArrayJSON);
		return boardJSON;
	}

	private static Board parseJsonToBoard(JSONObject boardJSON)
	{
		int width = (int) boardJSON.get(boardJSON);
		int length = (int) boardJSON.get(JSON_KEY_BOARD_LENGTH);
		Board board = new Board(length, width);

		JSONArray piecesArrayJSON = (JSONArray) boardJSON.get(JSON_KEY_BOARD_PIECES_ARRAY);
		int index = 0;
		Iterator<JSONObject> iterator = piecesArrayJSON.iterator();
		for (int ind = 0; iterator.hasNext(); ind++)
		{
			JSONObject pieceJSON = iterator.next();
			board.Pieces_[ind / width][ind % width] = parseJsonToPiece(pieceJSON);
		}
		return board;
	}

	/*
	 * This method is in place just in case down the line we need to add more
	 * key:value pairs when saving the Piece object.
	 */
	private static JSONObject getPieceJSON(Piece piece)
	{
		JSONObject pieceJSON = new JSONObject();
		pieceJSON.put(JSON_KEY_BOARD_PIECE_NAME, piece.getName());
		return pieceJSON;
	}

	private static Piece parseJsonToPiece(JSONObject pieceJSON)
	{
		String name = (String) pieceJSON.get(JSON_KEY_BOARD_PIECE_NAME);
		return new Piece(name);
	}
}
