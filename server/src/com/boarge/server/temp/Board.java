package com.boarge.server.temp;


public class Board extends Object{
	static final int NoTeam = 0;
	static final int Team1 = 1;
	static final int Team2 = -1;
	boolean pieceType[];
	public Piece Pieces_[][];
	public int width_;
	public int length_;
	boolean isHighlighted;

	public boolean team1QueenSideCast;
	public boolean team1KingSideCast;
	public boolean team2QueenSideCast;
	public boolean team2KingSideCast;
	public int enPass;
	
	public Board(int length, int width){
		width_ = width;
		length_ = length;
		Pieces_ = new Piece[width][length];
		
	}
	
	public void initBoard(){
		for (int i = 0; i < width_; i ++)
		{
			for (int j = 0; j< length_; j++)
			{
				Pieces_[i][j] = new EmptySpace(NoTeam,"");
			}
		}
	}
	public void putPiece(Piece piece, int x, int y){
		Pieces_[x][y] = piece;
	}
	public void removePiece(int x, int y){
		
		Pieces_[x][y] = new EmptySpace(NoTeam,"");
	}
	void clearAllHighlights(){
		for(int i=0;i<width_;i++)
			for(int j=0;j<length_;j++){
				Pieces_[i][j].highlight_ = false;
			}
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
