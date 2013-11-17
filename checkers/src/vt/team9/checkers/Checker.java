package vt.team9.checkers;

import java.util.List;

//import android.R;
import android.util.Log;

public class Checker extends Piece {

	Checker() {

	}

	Checker(int team) {
		super(team);
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.maroon_checker;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.orange_checker;
	}

	@Override
	public int getHighlightImage() {
		return R.drawable.jade_circle;
	}

	// This is the double jump action.
	@Override
	public void action1(int x, int y, Board board) {
		if (board.Pieces_[x][y].team_ == board.Team1) {
			if ((x + 2 < board.width_) && (y + 2 < board.length_)) {
				if ((board.Pieces_[x + 1][y + 1].team_ == board.Team2)
						&& (board.Pieces_[x + 2][y + 2].team_ == board.NoTeam)) {
					board.Pieces_[x + 2][y + 2].setHighlighted(true);
				}
			}
			if ((x - 2 >= 0) && (y + 2 < board.length_)) {
				if ((board.Pieces_[x - 1][y + 1].team_ == board.Team2)
						&& (board.Pieces_[x - 2][y + 2].team_ == board.NoTeam))
					board.Pieces_[x - 2][y + 2].setHighlighted(true);
			}
		} else if (board.Pieces_[x][y].team_ == board.Team2) {
			if ((x + 2 < board.width_) && (y - 2 >= 0)) {
				if ((board.Pieces_[x + 1][y - 1].team_ == board.Team1)
						&& (board.Pieces_[x + 2][y - 2].team_ == board.NoTeam))
					board.Pieces_[x + 2][y - 2].setHighlighted(true);
			}
			if ((x - 2 >= 0) && (y - 2 >= 0)) {
				if ((board.Pieces_[x - 1][y - 1].team_ == board.Team1)
						&& (board.Pieces_[x - 2][y - 2].team_ == board.NoTeam))
					board.Pieces_[x - 2][y - 2].setHighlighted(true);
			}
		}

	}

	@Override
	public void getMoves(int x, int y, Board board) {

		if (board.Pieces_[x][y].team_ == board.Team1) {
			if (y + 1 < board.length_) {
				if ((x + 1 < board.width_)
						&& (board.Pieces_[x + 1][y + 1].team_ == board.NoTeam))
					board.Pieces_[x + 1][y + 1].setHighlighted(true);
				if ((x - 1 >= 0) && (board.Pieces_[x - 1][y + 1].team_ == 0))
					board.Pieces_[x - 1][y + 1].setHighlighted(true);
				if ((x + 2 < board.width_) && (y + 2 < board.length_)) {
					if ((board.Pieces_[x + 1][y + 1].team_ == board.Team2)
							&& (board.Pieces_[x + 2][y + 2].team_ == board.NoTeam)) {
						board.Pieces_[x + 2][y + 2].setHighlighted(true);
					}
				}
				if ((x - 2 >= 0) && (y + 2 < board.length_)) {
					if ((board.Pieces_[x - 1][y + 1].team_ == board.Team2)
							&& (board.Pieces_[x - 2][y + 2].team_ == board.NoTeam))
						board.Pieces_[x - 2][y + 2].setHighlighted(true);
				}
			}
		} else if (board.Pieces_[x][y].team_ == board.Team2) {
			if (y - 1 >= 0) {
				if ((x + 1 < board.width_)
						&& (board.Pieces_[x + 1][y - 1].team_ == board.NoTeam))
					board.Pieces_[x + 1][y - 1].setHighlighted(true);
				if ((x - 1 >= 0) && (board.Pieces_[x - 1][y - 1].team_ == 0))
					board.Pieces_[x - 1][y - 1].setHighlighted(true);
				if ((x + 2 < board.width_) && (y - 2 >= 0)) {
					if ((board.Pieces_[x + 1][y - 1].team_ == board.Team1)
							&& (board.Pieces_[x + 2][y - 2].team_ == board.NoTeam))
						board.Pieces_[x + 2][y - 2].setHighlighted(true);
				}
				if ((x - 2 >= 0) && (y - 2 >= 0)) {
					if ((board.Pieces_[x - 1][y - 1].team_ == board.Team1)
							&& (board.Pieces_[x - 2][y - 2].team_ == board.NoTeam))
						board.Pieces_[x - 2][y - 2].setHighlighted(true);
				}
			}

		}

	}

}
