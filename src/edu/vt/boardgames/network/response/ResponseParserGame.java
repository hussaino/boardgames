package edu.vt.boardgames.network.response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.UtilsJSON;

/**
 * Created by Justin on 11/21/13
 */
public class ResponseParserGame extends ResponseParserJSON<Game>
{
	@Override
	protected Game getObjFromJSON(JSONObject json) throws JSONException
	{
		return UtilsJSON.getGameFromJSON(json);
	}
}
