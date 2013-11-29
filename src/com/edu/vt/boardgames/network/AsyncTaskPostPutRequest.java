package edu.vt.boardgames.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import edu.vt.boardgames.debug.MyLogger;

/**
 * Created by Justin on 10/2/13.
 */
public class AsyncTaskPostPutRequest extends AsyncTask<HttpEntity, Void, Void>
{
	private Handler m_handler;
	private short m_msgType;
	private String m_keyMsgType;
	private String m_keyResponse;
	private String m_resourcePath;
	private boolean m_isHttpPost;

	public AsyncTaskPostPutRequest(Handler handler, String keyMsgType, short msgType,
			String keyResponse, String resourcePath, boolean isHttpPost)
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
		m_isHttpPost = isHttpPost;
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
			HttpEntityEnclosingRequestBase httpMethod;
			if (m_isHttpPost)
			{
				httpMethod = new HttpPost(m_resourcePath);
			}
			else
			{
				httpMethod = new HttpPut(m_resourcePath);
			}

			if (postEntities.length > 0)
			{
				// sets the post request as the resulting string
				httpMethod.setEntity(postEntities[0]);

				// sets a request header so the page receving the request
				// will know what to do with it
				httpMethod.setHeader("Accept", "application/json");
				httpMethod.setHeader("Content-type", "application/json");
			}

			HttpResponse httpResponse = httpClient.execute(httpMethod);

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
		catch (IOException e)
		{
			MyLogger.logExceptionSevere(AsyncTaskPostPutRequest.class.getName(), "doInBackground",
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
