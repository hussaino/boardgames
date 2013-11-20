package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.boarge.server.Database;
import com.boarge.server.DatabaseBoards;

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

		String strResp = "";
		try
		{
			strResp = "boards: " + DatabaseBoards.getBoards() + "\n" + "addr: " + addr + "\n"
					+ "host: " + host + "\n" + "url: " + url + "\n";
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		resp.getWriter().write("Data Servlet!\n" + strResp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/plain");

		String board = req.getParameter("board");
		String addr = req.getRemoteAddr();
		String host = req.getRemoteHost();
		String url = req.getRequestURL().toString();

		String response = "addr: " + addr + "\n" + "host: " + host + "\n" + "url: " + url + "\n";

		try
		{
			DatabaseBoards.addBoard(board);
			response += "Success.";
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response += "Fail.";
		}
		resp.getWriter().write("Data Servlet doPost() \n" + response);
	}
}
