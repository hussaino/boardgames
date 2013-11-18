package vt.team9.checkers;

public class Bishop extends Piece {

	Bishop() {

	}

	Bishop(int team,String name) {
		super(team,name);
	}
	@Override
	public int getTeam1Image() {
		return R.drawable.team1_bishop;
	}

	@Override
	public int getTeam2Image() {
		return R.drawable.team2_bishop;
	}

	@Override
	public int getHighlightImage() {
		if (this.team_ == 1) return R.drawable.team1_bishop_highlight;
		else if(this.team_ == -1) return R.drawable.team2_bishop_highlight;
		else return 0;
	}
	
	@Override
	public void getMoves(int x, int y, Board board)
	{
		int oldX = x;
		int oldY = y;
		while ((oldY > 0) && (oldX > 0)) {
			oldY--;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY > 0) && (oldX < board.width_ - 1)) {
			oldY--;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY < board.length_ - 1) && (oldX > 0)) {
			oldY++;
			oldX--;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != board.NoTeam)
				break;
		}
		oldX = x;
		oldY = y;
		while ((oldY < board.length_ - 1) && (oldX < board.width_ - 1)) {
			oldY++;
			oldX++;
			if (board.Pieces_[oldX][oldY].team_ != this.team_)
				board.Pieces_[oldX][oldY].setHighlighted(true);
			if (board.Pieces_[oldX][oldY].team_ != board.NoTeam)
				break;
		}
	}

}
