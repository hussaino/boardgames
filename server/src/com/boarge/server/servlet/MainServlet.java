package com.boarge.server.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.boarge.server.database.GamesTable;
import com.boarge.server.database.TeamsTable;
import com.boarge.server.database.UsersGamesTable;
import com.boarge.server.database.UsersTable;

public class MainServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6702193108118827420L;
	private static final String DB_NAME = "Boarge";

	public static void main(String[] args) throws Exception
	{

		Server server = new Server(80);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME + ".db");
		System.out.println("Opened database");
		UsersTable.init(conn);
		TeamsTable.init(conn);
		UsersGamesTable.init(conn);
		GamesTable.init(conn);

		server.start();
		server.join();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().write("Main Servlet!\n");
	}

}
