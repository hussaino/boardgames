package vt.team9.checkers;

public class ChessKing extends Piece {
	ChessKing() {

	}

	ChessKing(int team) {
		super(team);
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.team1_king;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.team2_king;
	}

	@Override
	public int getHighlightImage() {
		if (this.team_ == 1)
			return R.drawable.team1_king_highlight;
		else if (this.team_ == -1)
			return R.drawable.team2_king_highlight;
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
		if (oldX < board.width_ - 1) {
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);

		}
		oldX = x;
		if (oldX > 0) {
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);

		}
		oldX = x;
		if (oldY < board.length_ - 1) {
			oldY++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);

		}
		oldY = y;
		if (oldY > 0) {
			oldY--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
		}
		oldX = x;
		oldY = y;
		if ((oldY > 0) && (oldX > 0)) {
			oldY--;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
		}
		oldX = x;
		oldY = y;
		if ((oldY > 0) && (oldX < board.width_ - 1)) {
			oldY--;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
		}
		oldX = x;
		oldY = y;
		if ((oldY < board.length_ - 1) && (oldX > 0)) {
			oldY++;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
		}
		oldX = x;
		oldY = y;
		if ((oldY < board.length_ - 1) && (oldX < board.width_ - 1)) {
			oldY++;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);

		}

	}

}
