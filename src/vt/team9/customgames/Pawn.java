package vt.team9.customgames;

import android.util.Log;
import edu.vt.boardgames.R;

public class Pawn extends Piece {

	Pawn() {

	}

	Pawn(int team,String name) {
		super(team,name);
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.team1_pawn;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.team2_pawn;
	}

	@Override
	public int getHighlightImage() {
		if (this.team_ == 1)
			return R.drawable.team1_pawn_highlight;
		else if (this.team_ == -1)
			return R.drawable.team2_pawn_highlight;
		else
			return 0;
	}

	@Override
	public void action1(int x, int y, Board board) {

	}

	@Override
	public void getMoves(int x, int y, Board board) {
		if (this.team_ == Board.Team1) {
			if (y + 1 < board.length_ && board.Pieces_[x][y+1].team_ == Board.NoTeam)
				board.Pieces_[x][y + 1].setHighlighted(true);
			if (y == 1 && board.Pieces_[x][y+2].team_ == Board.NoTeam)
				board.Pieces_[x][y + 2].setHighlighted(true);
			if (x+1 < board.width_ && y+1 < board.length_ && board.Pieces_[x+1][y+1].team_ == -this.team_ )
				board.Pieces_[x+1][y+1].setHighlighted(true);
			if (x-1 >= 0 && y+1 < board.length_ && board.Pieces_[x-1][y+1].team_ == -this.team_ )
				board.Pieces_[x-1][y+1].setHighlighted(true);
			
			if(board.enPass == x + 1){
				if (x+1 < board.width_ && y+1 < board.length_ && board.Pieces_[x+1][y].team_ == -this.team_ )
					board.Pieces_[x+1][y+1].setHighlighted(true);
			}
			else if(board.enPass == x - 1){
				if (x-1 >= 0 && y+1 < board.length_ && board.Pieces_[x-1][y].team_ == -this.team_ )
					board.Pieces_[x-1][y+1].setHighlighted(true);
			}
			
		}
		if (this.team_ == Board.Team2) {
			if (y - 1 >= 0 && board.Pieces_[x][y-1].team_ == Board.NoTeam)
				board.Pieces_[x][y - 1].setHighlighted(true);
			if (y == 6 && board.Pieces_[x][y-1].team_ == Board.NoTeam)
				board.Pieces_[x][y - 2].setHighlighted(true);
			if (x-1 >= 0 && y-1 > 0 && board.Pieces_[x-1][y-1].team_ == -this.team_ )
				board.Pieces_[x-1][y-1].setHighlighted(true);
			if (x+1 < board.width_ && y-1 >= 0 && board.Pieces_[x+1][y-1].team_ == -this.team_ )
				board.Pieces_[x+1][y-1].setHighlighted(true);
			
			if(board.enPass == x - 1){
				if (x-1 >= 0 && y-1 > 0 && board.Pieces_[x-1][y].team_ == -this.team_ )
					board.Pieces_[x-1][y-1].setHighlighted(true);
			}
			else if(board.enPass == x + 1){
				if (x+1 < board.width_ && y-1 >= 0 && board.Pieces_[x+1][y].team_ == -this.team_ )
					board.Pieces_[x+1][y-1].setHighlighted(true);
			}
			
		}
	}
}
