package vt.team9.customgames;

import edu.vt.boardgames.R;

public class EmptySpace extends Piece
{
	public EmptySpace()
	{
		super();
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

	EmptySpace(int team, String name)
	{
		super(team, name);
	}
}
