package edu.vt.boardgames.network.response;

import java.io.InputStream;

/**
 * Created by Justin on 10/7/13.
 */
public class ResponseStream {
	private InputStream m_inputStream;
	private boolean m_doneFetchingFlag;

	public ResponseStream(InputStream is) {
		m_inputStream = is;
		m_doneFetchingFlag = false;
	}

	public ResponseStream() {
		m_doneFetchingFlag = true;
	}

	public boolean isDoneFetching() {
		return m_doneFetchingFlag;
	}

	public InputStream getInputStream() {
		return m_inputStream;
	}
}
