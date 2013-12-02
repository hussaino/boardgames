package edu.vt.boardgames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.debug.MyLogger;

/**
 * Created by Justin on 11/21/13. A resource parser that expects a single
 * JSONObject object to be within an input stream. Use this after calling http
 * GET and getting a single JSON response element.
 */
public class ResponseParserGame extends ResponseParser<Game>
{
	@Override
	public ArrayList<Game> getResourceParsedElems(InputStream is)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String read;

		try
		{
			while ((read = br.readLine()) != null)
			{
				sb.append(read);
			}
		}
		catch (IOException e)
		{
			MyLogger.logExceptionSevere(ResponseParserGame.class.getName(),
					"getResourceParsedElems", null, e);
		}

		ArrayList<Game> boardsParsedFromInputStream = new ArrayList<Game>();
		try
		{

			JSONArray jsonBoards = new JSONArray(sb.toString());
			for (int i = 0; i < jsonBoards.length(); i++)
			{
				JSONObject jsonBoard = jsonBoards.getJSONObject(i);
				boardsParsedFromInputStream.add(UtilsJSON.getGameFromJSON(jsonBoard));
			}
		}
		catch (JSONException e)
		{
			MyLogger.logExceptionSevere(ResponseParserGame.class.getName(),
					"getResourceParsedElems", null, e);
		}

		return boardsParsedFromInputStream;
	}
}
