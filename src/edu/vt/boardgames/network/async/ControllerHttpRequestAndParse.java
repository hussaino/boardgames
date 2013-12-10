package edu.vt.boardgames.network.async;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.http.client.methods.HttpRequestBase;

import edu.vt.boardgames.network.response.ResponseParser;
import edu.vt.boardgames.network.response.ResponseStream;
import android.os.Handler;

/**
 * Created by Justin on 10/2/13. Starts thread to make http request and a thread
 * to parse the response of the request. Messages are passed between threads via
 * the queue.
 */
public class ControllerHttpRequestAndParse<T> {
	public void fetchAndParseRequests(Handler handler,
			ResponseParser<T> responseParser, HttpRequestBase... requests) {
		ArrayBlockingQueue<ResponseStream> blockingQueue = new ArrayBlockingQueue<ResponseStream>(
				requests.length + 1); // +1 because DONE signal is sent
		new AsyncTaskHttpRequest(blockingQueue).execute(requests);
		new AsyncTaskParseResource<T>(blockingQueue, responseParser, handler)
				.execute();
	}

}
