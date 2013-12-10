package edu.vt.boardgames.network.response;

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
 * Created by Justin on 11/21/13.
 */
public abstract class ResponseParserJSON<T> extends ResponseParser<T> {
	@Override
	public ArrayList<T> getResourceParsedElems(InputStream is) {
		String response = getResponse(is);
		ArrayList<T> objParsedFromResponse = new ArrayList<T>();
		try {
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				objParsedFromResponse.add(getObjFromJSON(json));
			}
		} catch (JSONException e) {
			MyLogger.logExceptionSevere(ResponseParserJSON.class.getName(),
					"getResourceParsedElems", null, e);
		}

		return objParsedFromResponse;
	}

	private String getResponse(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String read;

		try {
			while ((read = br.readLine()) != null) {
				sb.append(read);
			}
		} catch (IOException e) {
			MyLogger.logExceptionSevere(ResponseParserJSON.class.getName(),
					"getResourceParsedElems", null, e);
		}
		return sb.toString();
	}

	protected abstract T getObjFromJSON(JSONObject json) throws JSONException;
}
