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

public class MainServlet extends HttpServlet
{

	public static void main(String[] args) throws Exception
	{

		Server server = new Server(80);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);

		Database.init();
		DatabaseBoards.init();

		server.start();
		server.join();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		resp.setContentType("text/plain");
		try
		{
			resp.getWriter().write("Main Servlet!\n" + "Students:\n" + Database.getStudents());
		}
		catch (SQLException e)
		{
			resp.getWriter().write("Error");
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try
		{
			Integer id = Integer.valueOf(req.getParameter("id"));
			String name = req.getParameter("name");
			Integer age = Integer.valueOf(req.getParameter("age"));
			String grade = req.getParameter("grade");
			Database.addStudent(id, name, age, grade);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
