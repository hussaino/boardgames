package edu.vt.boardgames.network;

import java.util.ArrayList;

import vt.team9.customgames.Board;

public class Game {
	private int m_id;
	private Board m_gameState;
	private int m_turn;
	private boolean m_isPrivate;
	private boolean m_isRanked;
	private int m_difficulty;
	private int m_numTeams;
	private int m_numPlayersPerTeam;
	private int m_timeLimitPerMove;
	private int m_turnStrategy;
	private ArrayList<User> m_players;

	public Game(Boolean isPrivate, Boolean isRanked, Integer difficulty,
			Integer numTeams, Integer numPlayersPerTeam,
			Integer timeLimitPerMove, Integer turnStrategy) {
		m_isPrivate = isPrivate != null ? isPrivate : false;
		m_isRanked = isRanked != null ? isRanked : false;
		m_difficulty = difficulty != null ? difficulty : 5;
		m_numTeams = numTeams != null ? numTeams : -1;
		m_numPlayersPerTeam = numPlayersPerTeam != null ? numPlayersPerTeam
				: -1;
		m_timeLimitPerMove = timeLimitPerMove != null ? timeLimitPerMove : -1;
		m_turnStrategy = turnStrategy != null ? turnStrategy : -1;
		m_players = new ArrayList<User>();
	}

	public Board getBoard() {
		return m_gameState;
	}

	public void setBoard(Board board) {
		m_gameState = board;
	}

	public int getId() {
		return m_id;
	}

	public void setId(int m_id) {
		this.m_id = m_id;
	}

	public int getTurn() {
		return m_turn;
	}

	public void setTurn(int turn) {
		this.m_turn = turn;
	}

	public boolean isPrivate() {
		return m_isPrivate;
	}

	public void setPrivate(boolean m_isPrivate) {
		this.m_isPrivate = m_isPrivate;
	}

	public boolean isRanked() {
		return m_isRanked;
	}

	public void setRanked(boolean m_isRanked) {
		this.m_isRanked = m_isRanked;
	}

	public int getDifficulty() {
		return m_difficulty;
	}

	public void setDifficulty(int m_difficulty) {
		this.m_difficulty = m_difficulty;
	}

	public int getNumTeams() {
		return m_numTeams;
	}

	public void setNumTeams(int m_numTeams) {
		this.m_numTeams = m_numTeams;
	}

	public int getNumPlayersPerTeam() {
		return m_numPlayersPerTeam;
	}

	public void setNumPlayersPerTeam(int m_numPlayersPerTeam) {
		this.m_numPlayersPerTeam = m_numPlayersPerTeam;
	}

	public int getTimeLimitPerMove() {
		return m_timeLimitPerMove;
	}

	public void setTimeLimitPerMove(int m_timeLimitPerMove) {
		this.m_timeLimitPerMove = m_timeLimitPerMove;
	}

	public int getTurnStrategy() {
		return m_turnStrategy;
	}

	public void setTurnStrategy(int m_turnStrategy) {
		this.m_turnStrategy = m_turnStrategy;
	}

	public ArrayList<User> getPlayers() {
		return m_players;
	}

	public void setPlayers(ArrayList<User> players) {
		this.m_players = players;
	}

	public void addPlayers(User... players) {
		for (int i = 0; i < players.length; i++) {
			this.m_players.add(players[i]);
		}
	}

	public void clearAllPlayers() {
		this.m_players.clear();
	}

	public String toString() {
		return "Game{ " + "id: " + m_id + "\n gameState: " + m_gameState
				+ "\n turn: " + m_turn + "\n isPrivate: " + m_isPrivate
				+ "\n isRanked: " + m_isRanked + "\n difficulty: "
				+ m_difficulty + "\n numTeams: " + m_numTeams
				+ "\n numPlayersPerTeam: " + m_numPlayersPerTeam
				+ "\n timeLimitPerMove: " + m_timeLimitPerMove
				+ "\n turnStrategy: " + m_turnStrategy + "\nplayers: "
				+ getPlayersString() + " }\n";
	}

	private String getPlayersString() {
		String players = "{";
		for (User usr : m_players)
			players += usr + ", ";
		return players + "}";
	}
}
