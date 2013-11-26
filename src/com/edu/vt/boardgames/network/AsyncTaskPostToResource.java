package edu.vt.boardgames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import edu.vt.boardgames.debug.MyLogger;

/**
 * Created by Justin on 10/2/13.
 */
public class AsyncTaskPostToResource extends AsyncTask<HttpEntity, Void, Void>
{
	private Handler m_handler;
	private short m_msgType;
	private String m_keyMsgType;
	private String m_keyResponse;
	private String m_resourcePath;

	// private final JSONObject m_jsonObjToPost;

	/*
	 * private final Handler m_handler; private final String m_keyMsgType;
	 * private final short m_msgType; private final String m_keyResponse;
	 */

	public AsyncTaskPostToResource(Handler handler, String keyMsgType, short msgType,
			String keyResponse, String resourcePath/*
													 * , JSONObject
													 * jsonObjToPost
													 */)
	{
		/*
		 * Used to send callback response message to any Handler after the post
		 * has been made.
		 */
		m_handler = handler;
		m_keyMsgType = keyMsgType;
		m_msgType = msgType;
		m_keyResponse = keyResponse;

		// Used to make single post request.
		m_resourcePath = resourcePath;
		// m_jsonObjToPost = jsonObjToPost;
	}

	@Override
	protected Void doInBackground(HttpEntity... postEntities)
	{
		try
		{
			// instantiates httpclient to make request
			// TODO use HttpURLConnection
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// url with the post data
			HttpPost httpPost = new HttpPost(m_resourcePath);

			for (HttpEntity entitiy : postEntities)
			{

				// sets the post request as the resulting string
				httpPost.setEntity(entitiy);

				// sets a request header so the page receving the request
				// will know what to do with it
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");

				HttpResponse httpResponse = httpClient.execute(httpPost);

				// Extract string from response
				InputStream inputStream = httpResponse.getEntity().getContent();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				StringBuilder stringBuilder = new StringBuilder();
				String bufferedStrChunk = null;
				while ((bufferedStrChunk = bufferedReader.readLine()) != null)
				{
					stringBuilder.append(bufferedStrChunk);
				}

				sendResponseToHandler(stringBuilder.toString());
			}
		}
		catch (IOException e)
		{
			MyLogger.logExceptionSevere(AsyncTaskPostToResource.class.getName(), "doInBackground",
					null, e);
			sendResponseToHandler(null);
		}

		return null;
	}

	private void sendResponseToHandler(String response)
	{
		Bundle bundle = new Bundle();
		if (m_keyMsgType != null)
		{
			bundle.putShort(m_keyMsgType, m_msgType);
		}
		if (m_keyResponse != null && response != null)
		{
			bundle.putSerializable(m_keyResponse, response);
		}
		Message msg = new Message();
		msg.setData(bundle);
		m_handler.sendMessage(msg);
	}
}
