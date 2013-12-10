package vt.team9.customgames;

import edu.vt.boardgames.R;

public class Knight extends Piece {

	public Knight() {
		super();
	}

	Knight(int team, String name) {
		super(team, name);
	}

	@Override
	public int getTeam1Image() {
		return R.drawable.team1_knight;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.team2_knight;
	}

	@Override
	public int getHighlightImage() {
		if (this.team_ == 1)
			return R.drawable.team1_knight_highlight;
		else if (this.team_ == -1)
			return R.drawable.team2_knight_highlight;
		else
			return 0;
	}

	@Override
	public void action1(int x, int y, Board board) {

	}

	@Override
	public void getMoves(int x, int y, Board board) {
		if (x + 2 < board.width_ && y + 1 < board.length_
				&& board.Pieces_[x + 2][y + 1].team_ != this.team_)
			board.Pieces_[x + 2][y + 1].setHighlighted(true);
		if (x + 2 < board.width_ && y - 1 > 0
				&& board.Pieces_[x + 2][y - 1].team_ != this.team_)
			board.Pieces_[x + 2][y - 1].setHighlighted(true);
		if (x + 1 < board.width_ && y + 2 < board.length_
				&& board.Pieces_[x + 1][y + 2].team_ != this.team_)
			board.Pieces_[x + 1][y + 2].setHighlighted(true);
		if (x + 1 < board.width_ && y - 2 > 0
				&& board.Pieces_[x + 1][y - 2].team_ != this.team_)
			board.Pieces_[x + 1][y - 2].setHighlighted(true);

		if (x - 2 >= 0 && y + 1 < board.length_
				&& board.Pieces_[x - 2][y + 1].team_ != this.team_)
			board.Pieces_[x - 2][y + 1].setHighlighted(true);
		if (x - 2 >= 0 && y - 1 > 0
				&& board.Pieces_[x - 2][y - 1].team_ != this.team_)
			board.Pieces_[x - 2][y - 1].setHighlighted(true);
		if (x - 1 >= 0 && y + 2 < board.length_
				&& board.Pieces_[x - 1][y + 2].team_ != this.team_)
			board.Pieces_[x - 1][y + 2].setHighlighted(true);
		if (x - 1 >= 0 && y - 2 > 0
				&& board.Pieces_[x - 1][y - 2].team_ != this.team_)
			board.Pieces_[x - 1][y - 2].setHighlighted(true);
	}

}
