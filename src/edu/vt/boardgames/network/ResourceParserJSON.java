package edu.vt.boardgames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Justin on 11/21/13. A resource parser that expects a single
 * JSONObject object to be within an input stream. Use this after calling http
 * GET and getting a single JSON response element.
 */
public class ResourceParserJSON extends ResourceParser<JSONObject>
{
	@Override
	public ArrayList<JSONObject> getResourceParsedElems(InputStream is)
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
			e.printStackTrace();
		}

		// Only expect one JSONObject per GET request input stream.
		ArrayList<JSONObject> jsonObjectsParsedFromInputStream = new ArrayList<JSONObject>();
		try
		{
			jsonObjectsParsedFromInputStream.add(new JSONObject(sb.toString()));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return jsonObjectsParsedFromInputStream;
	}
}
