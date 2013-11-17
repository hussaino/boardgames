package vt.team9.checkers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GameController extends Object {

	PiecesAdapter adapter_;
	Board board_;
	Button submit_;
	boolean moved = false;
	int old_team;
	int gamePhase = 0;
	int currentTeam = 1;
	int oldX;
	int oldY;

	public GameController(PiecesAdapter adapter, Board board, Button submit) {
		adapter_ = adapter;
		board_ = board;
		submit_ = submit;
		board.initBoard();
		submit_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitClick();
			}
		
		});
	}

	public void submitClick() {
		// save the game state here.
		if (gamePhase == 2) {
			gamePhase = 0;
			moved = false;
			board_.clearAllHighlights();
			currentTeam = -currentTeam;
		
		Context context = MainActivity.getAppContext();
		CharSequence text = "Move submitted.";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show(); 
		}

	}

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
				Log.d("Checker", x + "," + y + ", " + currentTeam);
				if ((y == 0) && (currentTeam == board_.Team2)) {
					board_.putPiece(new King(board_.Pieces_[oldX][oldY].team_),
							x, y);
				} else if ((y == 7) && (currentTeam == board_.Team1)) {
					board_.putPiece(new King(board_.Pieces_[oldX][oldY].team_),
							x, y);
				} else {
					piece = board_.Pieces_[oldX][oldY];
					Class<? extends Piece> newPiece = piece.getClass();
					piece = newPiece.newInstance();
					piece.setTeam_(board_.Pieces_[oldX][oldY].team_);
					board_.putPiece(piece, x, y);
				}
				if (Math.abs(x - oldX) != 1) {
					board_.removePiece(x + ((oldX - x) / 2), y
							+ ((oldY - y) / 2));
				}
				board_.removePiece(oldX, oldY);
				board_.clearAllHighlights();
				oldX = x;
				oldY = y;
				gamePhase++;
				Context context = MainActivity.getAppContext();
				CharSequence text = "You can now submit your move.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show(); 
				

			} else if (board_.Pieces_[x][y].team_ != 0 && !moved) {
				board_.clearAllHighlights();
				piece = board_.Pieces_[x][y];
				// piece.setHighlighted(true);
				if (piece.team_ == currentTeam) {
					piece.getMoves(x, y, board_);
					oldX = x;
					oldY = y;
				}
			} else if (!moved) {
				board_.clearAllHighlights();
				gamePhase--;
			}
			else{
				
			}

			break;
		}
		case 2: {
			
			if ((x == oldX) && (y == oldY)) {
				Piece piece = board_.Pieces_[x][y];
				piece.action1(x, y, board_);
				oldX = x;
				oldY = y;
				moved = true;
				gamePhase = 1;

			}
			break;
		}

		}
		adapter_.notifyDataSetChanged();

	}

}
