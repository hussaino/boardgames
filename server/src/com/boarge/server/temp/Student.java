package com.boarge.server.temp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3640747712603059347L;
	int m_id;
	String m_name;
	int m_age;
	String m_grade;

	public Student(int id, String name, int age, String grade)
	{
		m_id = id;
		m_name = name;
		m_age = age;
		m_grade = grade;
	}
	
	public Student(ResultSet result) throws SQLException {
		m_id = -1;
		m_age = -1;
		
		while (result.next()) {
			m_id = result.getInt("id");
			m_name = result.getString("name");
			m_age = result.getInt("age");
			m_grade = result.getString("grade");
			System.out.println("Constructed: " + toString());
		}
	}

	@Override
	public String toString() {
		return "Student: " + m_name + "; id: " + m_id + "; age: " + m_age
				+ "; grade: " + m_grade + ";";
	}

}
