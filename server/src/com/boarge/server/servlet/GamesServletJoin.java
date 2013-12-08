package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.boarge.server.database.GamesTable;
import com.boarge.server.utils.UtilsServlet;

public class GamesServletJoin extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7509437876651068319L;
	private static final String URL_PARAM_USER_ID = "user";

	/**
	 * Used to create a new game.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		String response = "400";

		try
		{
			Integer gameId = UtilsServlet.getIntegerUrlPathInfo(req);
			String userId = req.getParameter(URL_PARAM_USER_ID);

			if (gameId >= 0)
			{
				// Get game with associated game id
				response = GamesTable.joinGame(Integer.valueOf(userId), gameId);
			}
		}
		catch (SQLException | JSONException e)
		{
			response = "400";
			e.printStackTrace();
		}
		catch (Exception e)
		{
			response = "400";
			e.printStackTrace();
		}

		if (response != null)
		{
			resp.getWriter().write(response);
		}
		else
		{
			resp.getWriter().write("400");
		}
	}
}
