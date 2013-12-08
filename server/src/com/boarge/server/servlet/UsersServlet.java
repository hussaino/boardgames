package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.boarge.server.database.UsersTable;
import com.boarge.server.utils.UtilsServlet;

public class UsersServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6977238587268777814L;

	/**
	 * Used to access the games table.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		String response = null;

		try
		{
			Integer userID = UtilsServlet.getIntegerUrlPathInfo(req);
			String userName = UtilsServlet.getStringUrlPathInfo(req);
			String gameId = req.getParameter("game");

			if (userID >= 0)
			{
				// Get game with associated game id
				response = UsersTable.getUser(userID);
			}
			else if (userName.length() > 0)
			{
				// Get all open games that fit game criteria
				response = UsersTable.getUser(userName);
			}
			else
			{
				if (gameId == null)
				{
					response = UsersTable.getAllUsers();
				}
				else
				{
					response = UsersTable.getUsersInGame(Integer.valueOf(gameId));
				}
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

	/**
	 * Used to create a new game.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");
		String response = null;

		// Extract the entity attached to the request here
		try
		{
			// Username should already be valid since signing in w/ g+.
			String userName = UtilsServlet.getStringUrlPathInfo(req);

			// If username was actually specified in url, create/login user.
			if (userName.length() > 0)
			{
				response = UsersTable.createOrLoginUser(userName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response += "Failed during Game creation.";
		}
		catch (Exception e)
		{
			// Trouble parsing URL parameters (most likely)
			e.printStackTrace();
			response += "Failed to parse URL.";
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

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");
		String response = null;

		try
		{
			Integer userID = UtilsServlet.getIntegerUrlPathInfo(req);
			String userName = UtilsServlet.getStringUrlPathInfo(req);

			if (userID >= 0)
			{
				// Get game with associated game id
				UsersTable.removeUser(userID);
				response = "200";
			}
			else if (userName.length() > 0)
			{
				// Get all open games that fit game criteria
				UsersTable.removeUser(userName);
				response = "200";
			}
		}
		catch (SQLException e)
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
