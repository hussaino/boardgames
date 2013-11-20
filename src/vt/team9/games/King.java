package vt.team9.games;

import vt.team9.customgames.R;

public class King extends Piece {
	King(){
		
	}
	King(int team,String name) {
		super(team,name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.maroon_king;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.ic_launcher;
	}

	@Override
	public int getHighlightImage() {
		return R.drawable.jade_circle;
	}

	public void getMoves(int x, int y, Board board) {

		if (y + 1 < board.length_) {
			if ((x + 1 < board.width_)
					&& (board.Pieces_[x + 1][y + 1].team_ == board.NoTeam))
				board.Pieces_[x + 1][y + 1].setHighlighted(true);
			if ((x - 1 >= 0) && (board.Pieces_[x - 1][y + 1].team_ == 0))
				board.Pieces_[x - 1][y + 1].setHighlighted(true);
			if ((x + 2 < board.width_) && (y + 2 < board.length_)) {
				if ((board.Pieces_[x + 1][y + 1].team_ == -board.Pieces_[x][y].team_)
						&& (board.Pieces_[x + 2][y + 2].team_ == board.NoTeam)) {
					board.Pieces_[x + 2][y + 2].setHighlighted(true);
				}
			}
			if ((x - 2 >= 0) && (y + 2 < board.length_)) {
				if ((board.Pieces_[x - 1][y + 1].team_ == -board.Pieces_[x][y].team_)
						&& (board.Pieces_[x - 2][y + 2].team_ == board.NoTeam))
					board.Pieces_[x - 2][y + 2].setHighlighted(true);
			}
		}
		if (y - 1 >= 0) {
			if ((x + 1 < board.width_)
					&& (board.Pieces_[x + 1][y - 1].team_ == board.NoTeam))
				board.Pieces_[x + 1][y - 1].setHighlighted(true);
			if ((x - 1 >= 0) && (board.Pieces_[x - 1][y - 1].team_ == 0))
				board.Pieces_[x - 1][y - 1].setHighlighted(true);
			if ((x + 2 < board.width_) && (y - 2 >= 0)) {
				if ((board.Pieces_[x + 1][y - 1].team_ == -board.Pieces_[x][y].team_)
						&& (board.Pieces_[x + 2][y - 2].team_ == board.NoTeam))
					board.Pieces_[x + 2][y - 2].setHighlighted(true);
			}
			if ((x - 2 >= 0) && (y - 2 >= 0)) {
				if ((board.Pieces_[x - 1][y - 1].team_ == -board.Pieces_[x][y].team_)
						&& (board.Pieces_[x - 2][y - 2].team_ == board.NoTeam))
					board.Pieces_[x - 2][y - 2].setHighlighted(true);
			}
		}
	}
	//the double jump
	@Override
	public void action1(int x, int y, Board board)
	{

		if ((x + 2 < board.width_) && (y + 2 < board.length_)) {
			if ((board.Pieces_[x + 1][y + 1].team_ == -board.Pieces_[x][y].team_)
					&& (board.Pieces_[x + 2][y + 2].team_ == board.NoTeam)) {
				board.Pieces_[x + 2][y + 2].setHighlighted(true);
			}
		}
		if ((x - 2 >= 0) && (y + 2 < board.length_)) {
			if ((board.Pieces_[x - 1][y + 1].team_ == -board.Pieces_[x][y].team_)
					&& (board.Pieces_[x - 2][y + 2].team_ == board.NoTeam))
				board.Pieces_[x - 2][y + 2].setHighlighted(true);
		}
		if ((x + 2 < board.width_) && (y - 2 >= 0)) {
			if ((board.Pieces_[x + 1][y - 1].team_ == -board.Pieces_[x][y].team_)
					&& (board.Pieces_[x + 2][y - 2].team_ == board.NoTeam))
				board.Pieces_[x + 2][y - 2].setHighlighted(true);
		}
		if ((x - 2 >= 0) && (y - 2 >= 0)) {
			if ((board.Pieces_[x - 1][y - 1].team_ == -board.Pieces_[x][y].team_)
					&& (board.Pieces_[x - 2][y - 2].team_ == board.NoTeam))
				board.Pieces_[x - 2][y - 2].setHighlighted(true);
		}
	}
}


