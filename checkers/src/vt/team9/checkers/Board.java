package vt.team9.checkers;

public class Board extends Object{
	static final int NoTeam = 0;
	static final int Team1 = 1;
	static final int Team2 = -1;
	boolean highlighted[][];
	boolean containsTeam1Piece[][];
	boolean containsTeam2Piece[][];
	boolean pieceType[];
	Piece Pieces_[][];
	int width_;
	int length_;
	boolean isHighlighted;
	
	
	Board(int length, int width){
		highlighted = new boolean[width][length];
		containsTeam1Piece= new boolean[width][length];
		containsTeam2Piece = new boolean[width][length];
		width_ = width;
		length_ = length;
		Pieces_ = new Piece[width][length];
		
	}
	
	public void initBoard(){
		for (int i = 0; i < width_; i ++)
		{
			for (int j = 0; j< length_; j++)
			{
				Pieces_[width_][length_] = new EmptySpace(NoTeam,"");
			}
		}
	}
	public void putPiece(Piece piece, int x, int y){
		Pieces_[x][y] = piece;
	}
	public void removePiece(int x, int y){
		
		Pieces_[x][y] = new EmptySpace(NoTeam,"");
	}
	void initPiecesTeam1(int x[],int y[]){
		for (int i = 0; i < x.length; i++){
			containsTeam1Piece[x[i]][y[i]] = true;
		}
	}

	void initPiecesTeam2(int x[],int y[]){
		for (int i = 0; i < x.length; i++){
			containsTeam2Piece[x[i]][y[i]] = true;
		}
	}
	void clearAllHighlights(){
		for(int i=0;i<width_;i++)
			for(int j=0;j<length_;j++){
				Pieces_[i][j].highlight_ = false;
			}
	}
	void highlightSpace(int x,int y){
		highlighted[x][y] = true;
		
	}
	void putTeam1Piece(int x,int y){
		containsTeam1Piece[x][y] = true;
	}
	void removeTeam1Piece(int x,int y){
		containsTeam1Piece[x][y] = false;
	}
	void putTeam2Piece(int x,int y){
		containsTeam2Piece[x][y] = true;
	}
	void removeTeam2Piece(int x,int y){
		containsTeam2Piece[x][y] = false;
	}
	public Piece getPiece(String name,int[] args){
		
		for(int i=0;i<width_;i++){
			for(int j=0;j<length_;j++){
				Piece temp = Pieces_[i][j];
				if(temp.getName() == name){
					args[0] = i;
					args[1] = j;
					return temp;
				}
			}
		}
		return new EmptySpace(NoTeam,"null");
	}

}
