package vt.team9.customgames;

public class Piece {

	boolean highlight_;
	int team_;
	String name_;

	public Piece() {

	}

	public int getTeam1Image() {
		return 0;
	}

	public int getTeam2Image() {
		return 0;
	}

	public int getHighlightImage() {
		return 0;
	}

	public Piece(int team, String name) {
		name_ = name;
		team_ = team;
	}

	public int getTeam_() {
		return team_;
	}

	public int setTeam_(int team) {
		return team_ = team;
	}

	public void setHighlighted(boolean set) {
		highlight_ = set;
	}

	public void getImage() {

	}

	public void getMoves(int x, int y, Board board) {
		board.isHighlighted = true;

	}

	public void action1(int x, int y, Board board) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}
}
