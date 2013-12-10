package edu.vt.boardgames.network;

public class Team {
	private int m_id;
	private String m_name;

	public Team(String name) {
		setName(name);
	}

	public int getId() {
		return m_id;
	}

	public void setId(int m_id) {
		this.m_id = m_id;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String m_name) {
		this.m_name = m_name;
	}

	public String toString() {
		return "{" + getId() + ": " + getName() + "}";
	}

}
