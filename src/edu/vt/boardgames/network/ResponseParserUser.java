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
public class ResponseParserUser extends ResponseParser<User>
{
	@Override
	public ArrayList<User> getResourceParsedElems(InputStream is)
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
			MyLogger.logExceptionSevere(ResponseParserUser.class.getName(),
					"getResourceParsedElems", null, e);
		}

		ArrayList<User> boardsParsedFromInputStream = new ArrayList<User>();
		try
		{

			JSONArray jsonUsers = new JSONArray(sb.toString());
			for (int i = 0; i < jsonUsers.length(); i++)
			{
				JSONObject jsonBoard = jsonUsers.getJSONObject(i);
				boardsParsedFromInputStream.add(UtilsJSON.getUserFromJSON(jsonBoard));
			}
		}
		catch (JSONException e)
		{
			MyLogger.logExceptionSevere(ResponseParserUser.class.getName(),
					"getResourceParsedElems", null, e);
		}

		return boardsParsedFromInputStream;
	}
}
