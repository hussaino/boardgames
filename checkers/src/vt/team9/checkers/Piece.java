package vt.team9.checkers;

import java.util.List;

public class Piece {
	
	boolean highlight_;
	int team_;
	Piece()
	{
		
	}
	public int getTeam1Image()
	{
		return 0;
	}
	public int getTeam2Image()
	{
		return 0;
	}
	public int getHighlightImage()
	{
		return 0;
	}
	Piece(int team)
	{
		team_ = team;
	}
	public int getTeam_() {
		return team_;
	}
	public int setTeam_(int team) {
		return team_ = team;
	}
	public void setHighlighted(boolean set)
	{
		highlight_ = set;
	}
	public void getImage(){
		
	}
	public void getMoves(int x, int y, Board board){	
			board.isHighlighted = true;
		
	}
	public void action1(int x, int y, Board board) {
		// TODO Auto-generated method stub
		
	}

}
