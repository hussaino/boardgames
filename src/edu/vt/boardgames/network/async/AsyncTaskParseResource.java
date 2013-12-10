package edu.vt.boardgames.network.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import edu.vt.boardgames.debug.MyLogger;
import edu.vt.boardgames.network.response.ResponseParser;
import edu.vt.boardgames.network.response.ResponseStream;

/**
 * Created by Justin on 10/2/13. This task acts as a consumer to the producer:
 * AsyncTaskFetchResource.java. It will continue to take ResourceInputs (from
 * array blocking queue) and parse them according to the ResourceParser passed
 * into the class. The final elements parsed are then sent to the Handler as a
 * bundle.
 */
public class AsyncTaskParseResource<T> extends AsyncTask<Object, Void, Void> {
	private ArrayBlockingQueue<ResponseStream> m_inputBlockingQueue;
	private ResponseParser<T> m_resourceParser;
	private Handler m_handler;

	public AsyncTaskParseResource(
			ArrayBlockingQueue<ResponseStream> inputProcessingQueue,
			ResponseParser<T> resourceParser, Handler handlerToSendParsedElems)

	{
		m_inputBlockingQueue = inputProcessingQueue;
		m_resourceParser = resourceParser;
		m_handler = handlerToSendParsedElems;
	}

	@Override
	protected Void doInBackground(Object... params) {
		try {
			// All elements parsed from the
			ArrayList<T> elementsParsed = new ArrayList<T>();
			ResponseStream resStream;
			/*
			 * Take resource streams from the blocking queue until the kill flag
			 * is sent from the producer thread.
			 */
			while (!(resStream = m_inputBlockingQueue.take()).isDoneFetching()) {
				if (!isCancelled()) {
					elementsParsed
							.addAll(m_resourceParser
									.getResourceParsedElems(resStream
											.getInputStream()));
				}
			}

			// The elements parsed from all requests are sent at once to handler
			sendParsedElemsToHandler(elementsParsed);
		}

		catch (InterruptedException e) {
			MyLogger.logExceptionSevere(AsyncTaskParseResource.class.getName(),
					"doInBackground", null, e);
		}

		return null;
	}

	private void sendParsedElemsToHandler(ArrayList<T> elementsParsed) {
		Bundle bundle = new Bundle();
		if (elementsParsed != null) {
			bundle.putSerializable("response", elementsParsed);
		}
		Message msg = new Message();
		msg.setData(bundle);
		m_handler.sendMessage(msg);
	}

}
