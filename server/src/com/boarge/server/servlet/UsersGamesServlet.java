package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boarge.server.database.UsersGamesTable;
import com.boarge.server.utils.UtilsServlet;

public class UsersGamesServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7754384216131287040L;

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
			Integer rowId = UtilsServlet.getIntegerUrlPathInfo(req);

			if (rowId >= 0)
			{
				// Get game with associated game id
				response = UsersGamesTable.getRow(rowId);
			}
			else
			{
				response = UsersGamesTable.getAllRows();
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
