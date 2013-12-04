package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.boarge.server.database.TeamsTable;
import com.boarge.server.utils.UtilsServlet;

public class TeamsServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6096833452014849075L;

	/**
	 * Used to access the games table.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		String response = "400";

		try
		{
			Integer teamID = UtilsServlet.getIntegerUrlPathInfo(req);

			if (teamID >= 0)
			{
				// Get game with associated game id
				response = TeamsTable.getTeam(teamID);
			}
			else
			{
				response = TeamsTable.getAllTeams();
			}
		}
		catch (SQLException | JSONException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
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
		String response = "400";

		// Extract the entity attached to the request here
		try
		{
			String teamName = UtilsServlet.getStringUrlPathInfo(req);
			if (isValidTeamName(teamName))
			{
				// Get all open games that fit game criteria
				response = TeamsTable.createTeam(teamName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
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

	private boolean isValidTeamName(String teamName)
	{
		// A character followed by a number/character/underscore.
		return teamName != null && teamName.matches("[a-zA-z]\\w+");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");
		String response = "400";

		try
		{
			Integer teamID = UtilsServlet.getIntegerUrlPathInfo(req);

			if (teamID >= 0)
			{
				// Get game with associated game id
				TeamsTable.removeTeam(teamID);
				response = "200";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		resp.getWriter().write(response);
	}

}
