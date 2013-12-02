package edu.vt.boardgames.network.response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsJSON;

/**
 * Created by Justin on 11/21/13.
 */
public class ResponseParserUser extends ResponseParserJSON<User>
{
	@Override
	protected User getObjFromJSON(JSONObject json) throws JSONException
	{
		return UtilsJSON.getUserFromJSON(json);
	}
}
