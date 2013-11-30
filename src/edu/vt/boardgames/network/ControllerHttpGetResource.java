package edu.vt.boardgames.network;

import java.util.concurrent.ArrayBlockingQueue;

import android.os.Handler;

/**
 * Created by Justin on 10/2/13.
 */
public class ControllerHttpGetResource<T>
{
	private String[] m_listUrls;
	private AsyncTaskFetchResource asyncFetchHtml;
	private AsyncTaskParseResource<T> asyncParseHtml;

	public ControllerHttpGetResource(ResourceParser<T> resourceParser,
			Handler handlerToReportParsedElems, String... listUrl)
	{
		ArrayBlockingQueue<ResourceStream> blockingQueue = new ArrayBlockingQueue<ResourceStream>(
				listUrl.length + 1); // +1 because DONE signal is sent
		asyncFetchHtml = new AsyncTaskFetchResource(blockingQueue);
		asyncParseHtml = new AsyncTaskParseResource<T>(blockingQueue, resourceParser,
				handlerToReportParsedElems);
		m_listUrls = listUrl;
	}

	public void fetchAndParseResources()
	{
		asyncFetchHtml.execute(m_listUrls);
		asyncParseHtml.execute();
	}
}
