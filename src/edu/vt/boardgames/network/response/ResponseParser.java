package edu.vt.boardgames.network.response;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Justin on 10/11/13. As long as you can take an input stream and
 * return an ArrayList of elements, you've met the criteria of being a Parser.
 * You can implement this interface to make parsers for html, JSON, or any other
 * type of formatted input stream.
 */
public abstract class ResponseParser<T> {
	public abstract ArrayList<T> getResourceParsedElems(InputStream is);
}
