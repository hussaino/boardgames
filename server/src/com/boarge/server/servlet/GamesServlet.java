package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.boarge.server.database.GamesTable;
import com.boarge.server.utils.UtilsJSON;
import com.boarge.server.utils.UtilsServlet;

public class GamesServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1258665950470025593L;
	private static final String URL_PARAM_USER_ID = "user";
	private static final String URL_PARAM_TURN = "turn";

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
			Integer gameID = UtilsServlet.getIntegerUrlPathInfo(req);
			String userID = req.getParameter(URL_PARAM_USER_ID);
			String teamID = req.getParameter("team");
			boolean isRetOpenGames = Boolean.valueOf(req.getParameter("open"));
			boolean isRetTurns = Boolean.valueOf(req.getParameter("turns"));
			boolean isRetGames = Boolean.valueOf(req.getParameter("games"));

			if (gameID >= 0)
			{
				// Get game with associated game id
				response = GamesTable.getGame(gameID);
			}
			else if (isRetOpenGames)
			{
				// Get all open games that fit game criteria
				response = GamesTable.getOpenGames();
			}
			else if (userID != null)
			{
				int usr = Integer.valueOf(userID);
				if (isRetTurns)
				{
					// Return all games where it is this users turn
					response = GamesTable.getUserTurns(usr);
				}
				else if (isRetGames)
				{
					// Return all games that this user is active in
					response = GamesTable.getUserGames(usr);
				}
			}
			else if (teamID != null)
			{
				if (isRetTurns)
				{
					// Return all games where it is this team's turn
					response = GamesTable.getTeamTurns(teamID);
				}
				else if (isRetGames)
				{
					// Return all games that this team is active in
					response = GamesTable.getTeamGames(teamID);
				}
			}
			else
			{
				// Return all games.
				response = GamesTable.getAllGames();
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
			boolean isPrivate = Boolean.valueOf(req.getParameter("private"));
			boolean isRanked = Boolean.valueOf(req.getParameter("ranked"));
			int difficulty = Integer.valueOf(req.getParameter("difficulty"));
			int numTeams = Integer.valueOf(req.getParameter("teams"));
			int numPlayersPerTeam = Integer.valueOf(req.getParameter("playersPerTeam"));
			int timeLimitPerMove = Integer.valueOf(req.getParameter("timeLimit"));
			int turnStrategy = Integer.valueOf(req.getParameter("turnStrat"));
			int userId = Integer.valueOf(req.getParameter(URL_PARAM_USER_ID));

			/*
			 * assume that we have a valid board object if JSONObject
			 * contruction did not throw exception and the map is not empty.
			 */
			response = GamesTable.createGame(isPrivate, isRanked, difficulty, numTeams,
					numPlayersPerTeam, timeLimitPerMove, turnStrategy, userId);
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

	/**
	 * Used to update a game state within a single game row. Ex. may be called
	 * when user submits a move. Notify all users that are in the game that are
	 * not associated with the user that called Put.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");

		String response = null;
		// Extract the entity attached to the request here
		try
		{
			// extract game id
			Integer gameID = UtilsServlet.getIntegerUrlPathInfo(req);
			String userID = req.getParameter(URL_PARAM_USER_ID);
			Integer nextTurn = Integer.valueOf(req.getParameter(URL_PARAM_TURN));

			String strEntity = UtilsServlet.getEntityFromInput(req.getInputStream());
			if (UtilsJSON.isValidJSON(strEntity))
			{
				/*
				 * assume that we have a valid board object if JSONObject
				 * contruction did not throw exception and the map is not empty.
				 */
				GamesTable.updateGameState(gameID, strEntity, nextTurn);
				// notify all members in game other than userID.
				System.out.print("Notification all but user: " + userID);

				response = "put new game state Success.";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response += "400";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			response += "400";
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			response += "400";
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
