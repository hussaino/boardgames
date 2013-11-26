package com.boarge.server.temp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database
{
	private static Connection c;

	public static void init()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:myfirst.db");
			System.out.println("Opened database");
			createTable(c);

			if (getStudent(c, 1).m_id == -1)
			{
				addStudents(c);
			}

			// addStudents(c);
			// addStudent(c, 4, "Delila", 21, "Undergrad");
			// addStudents(c);
			// removeStudent(c, 1);
			// removeStudent(c, 2);
			// removeStudent(c, 3);
			// updateStudentName(c, 1, "Justin");
			// updateStudentName(c, 3, "Leroy");
			// getStudents(c);
			// Student newStudent = new Student(7, "Jack", 22, "stupid");
			// storeObjectIntoDatabase(c, newStudent);
			// Student s = (Student) getObjectFromDatabase(c, 1);
			// System.out.println("Deserialized student: " + s.toString() +
			// "\n");

			// createPhoneTable(c);
			// addPhoneModel(c, "Galaxy S4");
			// getModels(c);

		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void createTable(Connection c) throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS class " + "(id INT PRIMARY KEY NOT NULL,"
				+ " name TEXT NOT NULL, " + " age INT NOT NULL, " + " grade TEXT)";
		PreparedStatement stmt = c.prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();
		System.out.println("Created table");
	}

	public static void addStudent(int id, String name, int age, String grade) throws SQLException
	{
		PreparedStatement stmt = c.prepareStatement("INSERT INTO class (id,name,age,grade) "
				+ "VALUES (" + id + ", '" + name + "', " + age + ", '" + grade + "');");
		stmt.executeUpdate();
		stmt.close();
	}

	public static void addStudents(Connection c) throws SQLException
	{
		PreparedStatement stmt = c.prepareStatement("INSERT INTO class (id,name,age,grade) "
				+ "VALUES (1, 'Bob', 26, 'PhD');");
		stmt.executeUpdate();
		stmt = c.prepareStatement("INSERT INTO class (id,name,age,grade) "
				+ "VALUES (2, 'Jenny', 20, 'Freshman');");
		stmt.executeUpdate();
		stmt = c.prepareStatement("INSERT INTO class (id,name,age,grade) "
				+ "VALUES (3, 'Robert', 23, 'Junior');");
		stmt.executeUpdate();
		stmt.close();
	}

	public static String getStudents() throws SQLException
	{
		String ret = "";
		PreparedStatement stmt = c.prepareStatement("SELECT * FROM class;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String grade = rs.getString("grade");
			ret += ("Got " + name + "; id: " + id + "; age: " + age + "; grade: " + grade + ";\n");
		}
		rs.close();
		stmt.close();
		return ret;
	}

	public static Student getStudent(Connection c, int id) throws SQLException
	{
		String sql = "SELECT * FROM class WHERE id=" + id + ";";
		PreparedStatement stmt = c.prepareStatement(sql);
		ResultSet result = stmt.executeQuery();
		Student s = new Student(result); // Constructor knows what values to
		// read from ResultSet
		stmt.close();
		return s;
	}

	public static void removeStudent(Connection c, int id) throws SQLException
	{
		PreparedStatement stmt = c.prepareStatement("DELETE FROM class WHERE id=?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}

	public static void updateStudentName(Connection c, int id, String newName) throws SQLException
	{
		// String sql = "UPDATE class SET name=" + newName + " WHERE id=" + id
		// + ";";
		// PreparedStatement stmt = c.prepareStatement("UPDATE class SET name="
		// + newName + " WHERE id=" + id + ";");
		PreparedStatement stmt = c.prepareStatement("UPDATE class SET name=? WHERE id=?");
		stmt.setString(1, newName);
		stmt.setInt(2, id);
		stmt.executeUpdate();
		stmt.close();
	}

	public static void storeObjectIntoDatabase(Connection c, Object o) throws SQLException,
			IOException, ClassNotFoundException
	{
		String sql = "CREATE TABLE IF NOT EXISTS objects (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, data BLOB NOT NULL);";
		PreparedStatement stmt = c.prepareStatement(sql);
		stmt.executeUpdate();

		// Convert object to byte[]
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		(new ObjectOutputStream(bstream)).writeObject(o);
		byte[] storable = bstream.toByteArray();

		// Store in database
		PreparedStatement insert = c.prepareStatement("INSERT INTO objects (data) VALUES (?)");
		insert.setBytes(1, storable);
		insert.executeUpdate();

	}

	public static Object getObjectFromDatabase(Connection c, int id) throws SQLException,
			IOException, ClassNotFoundException
	{

		// Extract from database
		PreparedStatement getData = c.prepareStatement("SELECT data FROM objects WHERE id=" + id
				+ ";");
		ResultSet rs = getData.executeQuery();
		byte[] retrieved = rs.getBytes("data");

		// Convert back to object (e.g "inflate")
		ByteArrayInputStream bin = new ByteArrayInputStream(retrieved);
		ObjectInputStream ois = new ObjectInputStream(bin);

		Object originalObject = ois.readObject();
		rs.close();
		return originalObject;
	}

	public static void createPhoneTable(Connection c) throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS phones (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, model TEXT NOT NULL);";
		PreparedStatement stmt = c.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public static void addPhoneModel(Connection c, String model) throws SQLException
	{
		PreparedStatement stmt = c.prepareStatement("INSERT INTO phones (model) " + "VALUES ('"
				+ model + "');");
		stmt.executeUpdate();
		stmt.close();
	}

	public static void getModels(Connection c) throws SQLException
	{
		PreparedStatement stmt = c.prepareStatement("SELECT * FROM phones;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			int id = rs.getInt("id");
			String model = rs.getString("model");
			System.out.println("Got id: " + id + "; model: " + model);
		}
		rs.close();
		stmt.close();
	}
}
