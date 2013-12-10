package edu.vt.boardgames.network.response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.network.Team;
import edu.vt.boardgames.network.UtilsJSON;

/**
 * Created by Justin on 11/21/13.
 */
public class ResponseParserTeam extends ResponseParserJSON<Team> {
	@Override
	protected Team getObjFromJSON(JSONObject json) throws JSONException {
		return UtilsJSON.getTeamFromJSON(json);
	}
}
