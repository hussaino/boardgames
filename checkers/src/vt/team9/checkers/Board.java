package vt.team9.checkers;

public class Board extends Object{
	boolean highlighted[][];
	boolean containsTeam1Piece[][];
	boolean containsTeam2Piece[][];
	boolean pieceType[];
	
	Board(int length, int width){
		highlighted = new boolean[width][length];
		containsTeam1Piece= new boolean[width][length];
		containsTeam2Piece = new boolean[width][length];
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
	void clearAll(){
		for(int i=0;i<highlighted.length;i++)
			for(int j=0;j<highlighted[i].length;j++){
				highlighted[i][j] = false;
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
	

}
