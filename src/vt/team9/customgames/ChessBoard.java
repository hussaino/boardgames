package vt.team9.customgames;

public class ChessBoard extends Board {


	public boolean team1QueenSideCast;
	public boolean team1KingSideCast;
	public boolean team2QueenSideCast;
	public boolean team2KingSideCast;
	public int enPass;
	
	public ChessBoard(int length, int width) {
		super(length, width);

		team1QueenSideCast = true;
		team1KingSideCast = true;
		team2QueenSideCast = true;
		team2KingSideCast = true;
		enPass = -1;
	}

	@Override
	public void initBoard(){
		for (int i = 0; i < width_; i ++)
		{
			for (int j = 0; j< length_; j++)
			{
				Pieces_[i][j] = new EmptySpace(NoTeam,"");
			}
		}
		
		for (int i = 0; i<8; i++) 
		{
			Pieces_[i][1] = new Pawn(Team1,"pawn" + (i+1));
			Pieces_[i][6] = new Pawn(Team2,"pawn" + (9 + i));
		}

		Pieces_[0][0] = new Rook(Team1,"rook1");
		Pieces_[7][0] = new Rook(Team1,"rook2");
		Pieces_[0][7] = new Rook(Team2,"rook3");
		Pieces_[7][7] = new Rook(Team2,"rook4");
		

		
		Pieces_[1][0] = new Knight(Team1,"knight1");
		Pieces_[6][0] = new Knight(Team1,"knight2");
		Pieces_[1][7] = new Knight(Team2,"knight3");
		Pieces_[6][7] = new Knight(Team2,"knight4");

		Pieces_[2][0] = new Bishop(Team1,"bishop1");
		Pieces_[5][0] = new Bishop(Team1,"bishop2");
		Pieces_[2][7] = new Bishop(Team2,"bishop3");
		Pieces_[5][7] = new Bishop(Team2,"bishop4");
		
		Pieces_[3][0] = new Queen(Team1,"queen1");
		Pieces_[4][0] = new ChessKing(Team1,"king1");
		
		Pieces_[3][7] = new Queen(Team2,"queen2");
		Pieces_[4][7] = new ChessKing(Team2,"king2");
		


	}

}
