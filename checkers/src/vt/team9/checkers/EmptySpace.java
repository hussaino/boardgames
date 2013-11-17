package vt.team9.checkers;

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
		return R.drawable.jade_square;
	}
	EmptySpace(int team) {
		super(team);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
