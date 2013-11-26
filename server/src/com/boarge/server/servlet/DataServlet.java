package com.boarge.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.boarge.server.database.Games;

public class DataServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");

		String addr = req.getRemoteAddr();
		String host = req.getRemoteHost();
		String url = req.getRequestURL().toString();

		String strResp = "boards: " + Games.getAllGames() + "\n" + "addr: " + addr + "\n"
				+ "host: " + host + "\n" + "url: " + url + "\n";

		resp.getWriter().write("Data Servlet!\n" + strResp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");

		// Extract any URL encoded data here as parameters.
		String addr = req.getRemoteAddr();
		String host = req.getRemoteHost();
		String url = req.getRequestURL().toString();

		String response = "addr: " + addr + "\n" + "host: " + host + "\n" + "url: " + url + "\n";

		// Extract the entity attached to the request here
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
			StringBuilder stringBoard = new StringBuilder();
			String read;

			while ((read = br.readLine()) != null)
			{
				stringBoard.append(read);
			}

			String strEntity = stringBoard.toString();
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
}
