package edu.vt.boardgames.network.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.vt.boardgames.debug.MyLogger;

/**
 * Created by Justin on 11/21/13.
 */
public class ResponseParserString extends ResponseParser<String>
{
	@Override
	public ArrayList<String> getResourceParsedElems(InputStream is)
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
			MyLogger.logExceptionSevere(ResponseParserString.class.getName(),
					"getResourceParsedElems", null, e);
		}

		ArrayList<String> stringRawResponse = new ArrayList<String>();
		stringRawResponse.add(sb.toString());
		return stringRawResponse;
	}
}
