package vt.team9.checkers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class ChessController extends GameController {

	boolean check;
	boolean checkmate;

	public ChessController(PiecesAdapter adapter, Board board, Button submit) {
		super(adapter, board, submit);
		// TODO Auto-generated constructor stub
		checkmate = false;
		check = false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	void itemClicked(int position) throws InstantiationException,
			IllegalAccessException {
		int x = position % 8;
		int y = position / 8;
		switch (gamePhase) {
		case 0: {
			Piece piece = board_.Pieces_[x][y];

			if (piece.team_ == currentTeam) {
				piece.getMoves(x, y, board_);
				oldX = x;
				oldY = y;
				gamePhase++;
			}

			break;
		}
		case 1: {
			Piece piece = board_.Pieces_[x][y];

			if (board_.Pieces_[x][y].highlight_) {
				piece = board_.Pieces_[oldX][oldY];
				Piece oldPiece = board_.Pieces_[x][y];
				board_.putPiece(piece, x, y);
				board_.removePiece(oldX, oldY);
				board_.clearAllHighlights();
				isInCheck();
				if (check) {
					Context context = MainActivity.getAppContext();
					CharSequence text = "You are still in Check";
					int duration = Toast.LENGTH_SHORT;
					board_.putPiece(piece, oldX, oldY);
					board_.removePiece(x, y);
					board_.putPiece(oldPiece, x, y);
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					board_.clearAllHighlights();
					adapter_.notifyDataSetChanged();
					gamePhase--;
					return;
				}
				gamePhase++;
				putInCheck();
				isCheckmate();
				if(checkmate){
					Log.d("Hussain","Checkmate");
				}
				Context context = MainActivity.getAppContext();
				CharSequence text = "You can now submit your move.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			} else if (board_.Pieces_[x][y].team_ == currentTeam) {
				board_.clearAllHighlights();
				piece = board_.Pieces_[x][y];
				piece.getMoves(x, y, board_);
				oldX = x;
				oldY = y;
			}
			else{
				board_.clearAllHighlights();
				gamePhase--;
			}
			
			if (check) {
				Log.d("Hussain", "Check");
			}
			break;
		}
		case 2: {
			break;
		}

		}
		adapter_.notifyDataSetChanged();

	}

	@Override
	public void submitClick() {
		// TODO Auto-generated method stub
		super.submitClick();

	}

	public void putInCheck() {

		Piece king;

		int coord[] = new int[2];
		if (currentTeam == -1) {
			king = board_.getPiece("king1", coord);
		} else
			king = board_.getPiece("king2", coord);

		for (int i = 0; i < board_.width_; i++) {
			for (int j = 0; j < board_.length_; j++) {
				Piece temp = board_.Pieces_[i][j];
				if (temp.team_ == currentTeam) {
					int tempcoord[] = new int[2];
					board_.getPiece(temp.getName(), tempcoord);
					board_.clearAllHighlights();
					temp.getMoves(tempcoord[0], tempcoord[1], board_);
					if (king.highlight_) {
						check = true;
					}
				}

			}
		}
		board_.clearAllHighlights();
	}

	public void isCheckmate() {
		if(!check){
			return;
		}
		boolean[][] possibleMoves = new boolean[board_.width_][board_.length_];
		Piece king;
		
		int coord[] = new int[2];
		if (currentTeam == -1) {
			king = board_.getPiece("king1", coord);
		} else
			king = board_.getPiece("king2", coord);
		
		board_.removePiece(coord[0], coord[1]);
		for (int i = 0; i < board_.width_; i++) {
			for (int j = 0; j < board_.length_; j++) {
				Piece temp = board_.Pieces_[i][j];
				if (temp.team_ == currentTeam) {
					int tempcoord[] = new int[2];
					board_.getPiece(temp.getName(), tempcoord);
					board_.clearAllHighlights();
					temp.getMoves(tempcoord[0], tempcoord[1], board_);
					if(temp.getClass() == Pawn.class){
						for(int k=0; k<board_.length_;k++){
							board_.Pieces_[tempcoord[0]][k].highlight_ = false;
						}
					}
					for(int m=0;m<board_.width_;m++){
						for(int n=0;n<board_.length_;n++){
							if(board_.Pieces_[m][n].highlight_){
								possibleMoves[m][n] = true;
							}
						}
					}
				}

			}
		}
		board_.clearAllHighlights();

		board_.putPiece(king, coord[0], coord[1]);
		king.getMoves(coord[0], coord[1], board_);
		for(int m=0;m<board_.width_;m++){
			for(int n=0;n<board_.length_;n++){
				if(board_.Pieces_[m][n].highlight_)
					if(possibleMoves[m][n] == false){
						checkmate = false;
						board_.clearAllHighlights();
						return;
					}
				
			}
		}
		board_.clearAllHighlights();
		
		for (int i = 0; i < board_.width_; i++) {
			for (int j = 0; j < board_.length_; j++) {
				Piece temp = board_.Pieces_[i][j];
				if (temp.team_ == -currentTeam) {
					int tempcoord[] = new int[2];
					board_.getPiece(temp.getName(), tempcoord);
					board_.clearAllHighlights();
					temp.getMoves(tempcoord[0], tempcoord[1], board_);
					for(int m=0;m<board_.width_;m++){
						for(int n=0;n<board_.length_;n++){
							Piece piece = board_.Pieces_[i][j];
							if(piece.getName() == "king1" || piece.getName() == "king2"){
								continue;
							}
							Piece oldPiece = board_.Pieces_[m][n];
							if(board_.Pieces_[m][n].highlight_ && possibleMoves[m][n] == true){
								board_.putPiece(piece, m, n);
								board_.removePiece(i, j);
								board_.clearAllHighlights();
								isInCheck();
								if (!check) {
									Log.d("Hussain","" + "(" + m + "," + n + ")");
									checkmate = false;
									board_.putPiece(piece, i, j);
									board_.removePiece(m, n);
									board_.putPiece(oldPiece, m, n);
									board_.clearAllHighlights();
									return;
								}
							}
								
						}
					}
				}

			}
			
		}
		
		checkmate = true;
		board_.clearAllHighlights();
	}

	public void isInCheck() {

		Piece king;

		int coord[] = new int[2];
		if (currentTeam == 1) {
			king = board_.getPiece("king1", coord);
		} else
			king = board_.getPiece("king2", coord);

		for (int i = 0; i < board_.width_; i++) {
			for (int j = 0; j < board_.length_; j++) {
				Piece temp = board_.Pieces_[i][j];
				if (temp.team_ == -currentTeam) {
					int tempcoord[] = new int[2];
					board_.getPiece(temp.getName(), tempcoord);
					board_.clearAllHighlights();
					temp.getMoves(tempcoord[0], tempcoord[1], board_);
					if (king.highlight_) {
						check = true;
						board_.clearAllHighlights();
						return;
					}
				}

			}
		}
		check = false;
		board_.clearAllHighlights();
	}

	public void resetRound() {

	}
}
