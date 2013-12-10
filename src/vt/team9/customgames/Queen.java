package vt.team9.customgames;

import edu.vt.boardgames.R;

public class Queen extends Piece {

	public Queen() {
		super();
	}

	Queen(int team, String name) {
		super(team, name);
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.team1_queen;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.team2_queen;
	}

	@Override
	public int getHighlightImage() {
		if (this.team_ == 1)
			return R.drawable.team1_queen_highlight;
		else if (this.team_ == -1)
			return R.drawable.team2_queen_highlight;
		else
			return 0;
	}

	@Override
	public void action1(int x, int y, Board board) {

	}

	@Override
	public void getMoves(int x, int y, Board board) {
		int oldX = x;
		int oldY = y;
		while (oldX < board.width_ - 1) {
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;

		}
		oldX = x;
		while (oldX > 0) {
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;

		}
		oldX = x;
		while (oldY < board.length_ - 1) {
			oldY++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;

		}
		oldY = y;
		while (oldY > 0) {
			oldY--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY > 0) && (oldX > 0)) {
			oldY--;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY > 0) && (oldX < board.width_ - 1)) {
			oldY--;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY < board.length_ - 1) && (oldX > 0)) {
			oldY++;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY < board.length_ - 1) && (oldX < board.width_ - 1)) {
			oldY++;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != Board.NoTeam)
				break;
		}

	}

}
