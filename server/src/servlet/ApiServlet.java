package com.boarge.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ApiServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");

		String param1 = req.getParameter("param1");
		String param2 = req.getParameter("param2");
		String addr = req.getRemoteAddr();
		String host = req.getRemoteHost();
		String url = req.getRequestURL().toString();

		String strResp = "param1: " + param1 + "\n" + "param2: " + param2
				+ "\n" + "addr: " + addr + "\n" + "host: " + host + "\n"
				+ "url: " + url + "\n";
		resp.getWriter().write("Api Servlet!\n" + strResp);
	}

}
