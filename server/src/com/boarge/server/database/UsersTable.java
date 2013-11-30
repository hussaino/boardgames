package com.boarge.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersTable
{
	private static Connection s_connection;

	public static void init()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			s_connection = DriverManager.getConnection("jdbc:sqlite:boards.db");
			System.out.println("Opened database");

			createBoardsTable();

			if (getBoards().length() < 1)
			{
				addBoard("Board object 1 JSON string");
				addBoard("Board object 2 JSON string");
				addBoard("Board object 3 JSON string");
			}

		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void createBoardsTable() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS boards (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, board TEXT NOT NULL);";
		PreparedStatement stmt = s_connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static void addBoard(String board) throws SQLException
	{
		PreparedStatement stmt = s_connection.prepareStatement("INSERT INTO boards (board) "
				+ "VALUES ('" + board + "');");
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getBoards() throws SQLException
	{
		String boards = "";
		PreparedStatement stmt = s_connection.prepareStatement("SELECT * FROM boards;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			boards += rs.getString("board") + "\n";
		}
		rs.close();
		stmt.close();

		return boards;
	}
}
