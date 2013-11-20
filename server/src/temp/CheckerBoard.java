package com.boarge.server.temp;

public class CheckerBoard extends Board
{

	public CheckerBoard(int length, int width)
	{
		super(length, width);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initBoard()
	{
		for (int i = 0; i < width_; i++)
		{
			for (int j = 0; j < length_; j++)
			{
				Pieces_[i][j] = new EmptySpace(NoTeam, "");
			}
		}
		for (int i = 0; i < width_; i += 2)
			Pieces_[i][0] = new Piece(Team1, "Hello");
		for (int i = 1; i < width_; i += 2)
			Pieces_[i][1] = new Piece(Team1, "Justin");
		for (int i = 0; i < width_; i += 2)
			Pieces_[i][2] = new Piece(Team1, "P1");

		for (int i = 1; i < width_; i += 2)
			Pieces_[i][5] = new Piece(Team2, "P2");
		for (int i = 0; i < width_; i += 2)
			Pieces_[i][6] = new Piece(Team2, "");
		for (int i = 1; i < width_; i += 2)
			Pieces_[i][7] = new Piece(Team2, "");

	}

}
