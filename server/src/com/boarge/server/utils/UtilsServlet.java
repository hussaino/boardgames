package com.boarge.server.utils;

import javax.servlet.http.HttpServletRequest;

public class UtilsServlet
{
	/*
	 * Return the integer attached to the end of the url. Return -1 if any
	 * parsing problems.
	 */
	public static Integer getIntegerUrlPathInfo(HttpServletRequest req)
	{
		Integer extractedInt = -1;
		String pathInfo = req.getPathInfo();
		String strExtractInfo = pathInfo == null ? "" : pathInfo.toString().replaceAll("/", "");
		if (strExtractInfo.length() > 0)
		{
			try
			{
				extractedInt = Integer.valueOf(strExtractInfo);
			}
			catch (NumberFormatException e)
			{

			}
		}
		return extractedInt;
	}

	/*
	 * Return any string that is attached to the end of the path
	 */
	public static String getStringUrlPathInfo(HttpServletRequest req)
	{
		String pathInfo = req.getPathInfo();
		return pathInfo == null ? "" : pathInfo.toString().replaceAll("/", "");
	}
}
