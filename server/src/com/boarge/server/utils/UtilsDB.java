package com.boarge.server.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UtilsDB
{
	public static int getLastInsertId(Connection conn) throws SQLException
	{
		String lastInsertCol = "last_insert_rowid()";
		String sql = "SELECT " + lastInsertCol;
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		int id = rs.getInt(lastInsertCol);
		stmt.close();
		return id;
	}

	public static String getSqlVal(boolean bool)
	{
		return bool ? "1" : "0";
	}

	public static String getSqlVal(Integer num)
	{
		return String.valueOf(num);
	}

	/*
	 * Surround string in single quotes
	 */
	public static String getSqlVal(String val)
	{
		return "'" + val + "'";
	}

	public static String getResultSetTable(ResultSet rs, String delimiter) throws SQLException
	{
		String table = "";

		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 0; i < meta.getColumnCount(); i++)
		{
			table += meta.getColumnLabel(i + 1) + delimiter;
		}

		table += "\n";

		while (rs.next())
		{
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
			{
				table += rs.getObject(i + 1).toString() + delimiter;
			}
			table += "\n";
		}

		return table;
	}
}
