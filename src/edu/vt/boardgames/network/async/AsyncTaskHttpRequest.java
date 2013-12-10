package edu.vt.boardgames.network.async;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import edu.vt.boardgames.debug.MyLogger;
import edu.vt.boardgames.network.response.ResponseStream;

/**
 * Created by Justin on 10/2/13.
 */
public class AsyncTaskHttpRequest extends
		AsyncTask<HttpRequestBase, Void, Void> {
	ArrayBlockingQueue<ResponseStream> m_blockingQueue;

	public AsyncTaskHttpRequest(ArrayBlockingQueue<ResponseStream> blockingQueue) {
		m_blockingQueue = blockingQueue;
	}

	@Override
	protected Void doInBackground(HttpRequestBase... requests) {
		try {
			for (int i = 0; i < requests.length; i++) {
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(requests[i]);
				InputStream is = response.getEntity().getContent();
				ResponseStream source = new ResponseStream(is);
				m_blockingQueue.put(source);
			}
			m_blockingQueue.put(new ResponseStream());
		} catch (IOException e) {
			MyLogger.logExceptionSevere(AsyncTaskHttpRequest.class.getName(),
					"doInBackground", null, e);
		} catch (InterruptedException e) {
			MyLogger.logExceptionSevere(AsyncTaskHttpRequest.class.getName(),
					"doInBackground", null, e);
		}
		return null;
	}
}
