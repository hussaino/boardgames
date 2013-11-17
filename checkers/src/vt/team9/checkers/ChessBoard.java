package vt.team9.checkers;

public class ChessBoard extends Board {

	ChessBoard(int length, int width) {
		super(length, width);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initBoard(){
		for (int i = 0; i < width_; i ++)
		{
			for (int j = 0; j< length_; j++)
			{
				Pieces_[i][j] = new EmptySpace(NoTeam);
			}
		}
		for (int i = 0; i<width_; i+=1) 
		{
			Pieces_[i][1] = new Pawn(Team1);
			Pieces_[i][6] = new Pawn(Team2);
		}
		for (int i = 0; i<8; i+=7) 
		{
			Pieces_[i][0] = new Rook(Team1);
			Pieces_[i][7] = new Rook(Team2);
		}
		

		
		Pieces_[1][0] = new Knight(Team1);
		Pieces_[6][0] = new Knight(Team1);
		Pieces_[1][7] = new Knight(Team2);
		Pieces_[6][7] = new Knight(Team2);

		Pieces_[2][0] = new Bishop(Team1);
		Pieces_[5][0] = new Bishop(Team1);
		Pieces_[2][7] = new Bishop(Team2);
		Pieces_[5][7] = new Bishop(Team2);
		
		Pieces_[4][0] = new Queen(Team1);
		Pieces_[3][0] = new ChessKing(Team1);
		
		Pieces_[3][7] = new Queen(Team2);
		Pieces_[4][7] = new ChessKing(Team2);
		


		

	}

}
