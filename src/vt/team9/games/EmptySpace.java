package vt.team9.games;

import vt.team9.customgames.R;

public class EmptySpace extends Piece {
	EmptySpace(){
		
	}
	@Override
	public int getTeam1Image()
	{
		return 0;
	}
	@Override
	public int getTeam2Image()
	{
		return 0;
	}
	@Override
	public int getHighlightImage()
	{
		return R.drawable.empty_space_highlight;
	}
	EmptySpace(int team,String name) {
		super(team,name);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}