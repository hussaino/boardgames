package com.boarge.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.boarge.server.database.Games;

public class GamesServlet extends HttpServlet
{
	/**
	 * Used to access the games table.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		String response = "Invalid GET request";

		String url = req.getRequestURL().toString();
		Integer gameID = -1;
		String extractGameId = url.substring(url.lastIndexOf("/"));
		try
		{
			gameID = Integer.valueOf(extractGameId);
		}
		catch (NumberFormatException e)
		{
			// Game ID was not specified. Do nothing.
		}

		String gameCriteria = req.getParameter("criteria");
		String userID = req.getParameter("user");
		String teamID = req.getParameter("team");
		boolean isRetTurns = Boolean.valueOf(req.getParameter("turns"));
		boolean isRetGames = Boolean.valueOf(req.getParameter("games"));

		if (gameID >= 0)
		{
			// Get game with associated game id
			response = Games.getGame(gameID);
		}
		else if (gameCriteria != null)
		{
			// Get all open games that fit game criteria
			response = Games.getOpenGames(gameCriteria);
		}
		else if (userID != null)
		{
			if (isRetTurns)
			{
				// Return all games where it is this users turn
				response = Games.getUserTurns(userID);
			}
			else if (isRetGames)
			{
				// Return all games that this user is active in
				response = Games.getUserGames(userID);
			}
		}
		else if (teamID != null)
		{
			if (isRetTurns)
			{
				// Return all games where it is this team's turn
				response = Games.getTeamTurns(teamID);
			}
			else if (isRetGames)
			{
				// Return all games that this team is active in
				response = Games.getTeamGames(teamID);
			}
		}
		else
		{
			// Return all games.
			response = Games.getAllGames();
		}

		resp.getWriter().write(response);
	}

	/**
	 * Used to create a new game.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");

		// Extract any URL encoded data here as parameters.
		String board = req.getParameter("board");
		String addr = req.getRemoteAddr();
		String host = req.getRemoteHost();
		String url = req.getRequestURL().toString();

		String response = "addr: " + addr + "\n" + "host: " + host + "\n" + "url: " + url + "\n";

		// Extract the entity attached to the request here
		try
		{

			String strEntity = getEntityFromInput(req.getInputStream());
			// JSONObject jsonMapEntities = new JSONObject(strEntity);
			// if (!jsonMapEntities.isEmpty())
			// {
			// /*
			// * assume that we have a valid board object if JSONObject
			// * contruction did not throw exception and the map is not empty.
			// */
			// Games.addBoard(strEntity);
			// response += " Success.";
			// }
		}
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// response += "Failed.";
		// }
		catch (IOException e)
		{
			e.printStackTrace();
			response += "Failed.";
		}

		resp.getWriter().write("Data Servlet doPost() \n" + response);
	}

	/**
	 * Used to update a game state within a single game row. Ex. may be called
	 * when user submits a move.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		
		extract game id
		String userID = req.getParameter("user");
		

		String response = "";

		// Extract the entity attached to the request here
		try
		{
			String strEntity = getEntityFromInput(req.getInputStream());
			JSONObject jsonMapEntities = new JSONObject(strEntity);
			if (jsonMapEntities.length() > 0)
			{
				/*
				 * assume that we have a valid board object if JSONObject
				 * contruction did not throw exception and the map is not empty.
				 */
				Games.addBoard(strEntity);
				response += " Success.";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response += "Failed.";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			response += "Failed.";
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			response += "Failed.";
		}

		resp.getWriter().write("Data Servlet doPost() \n" + response);

	}

	private String getEntityFromInput(ServletInputStream inputStream) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBoard = new StringBuilder();
		String read;

		while ((read = br.readLine()) != null)
		{
			stringBoard.append(read);
		}

		return stringBoard.toString();
	}

}
