package vt.team9.customgames;

import edu.vt.boardgames.network.Game;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class ChessController extends GameController {

	boolean check;
	boolean checkmate;
	Activity main;
	boolean ready = false;

	// ChessBoard board;
	public ChessController(PiecesAdapter adapter, Board board, Button submit,
			Activity activity) {
		super(adapter, board, submit);
		checkmate = false;
		check = false;
		main = activity;
	}

	@Override
	public void setGame(Game game) {
		// TODO Auto-generated method stub
		super.setGame(game);
		if (game_.getPlayers().size() > 1) {
			ready = true;
			if (game_.getPlayers().get(0).getId() == thisUser.getId())
				thisTeam = 1;
			else
				thisTeam = -1;

		} else {
			thisTeam = -1;
			ready = false;
		}

	}

	@Override
	void itemClicked(int position) throws InstantiationException,
			IllegalAccessException {
		ChessBoard board = (ChessBoard) this.getBoard();
		if(!ready)
			Toast.makeText(submit_.getContext(),
					"Still waiting for another player to join",
					Toast.LENGTH_SHORT).show();
		
		int x = position % 8;
		int y = position / 8;
		switch (gamePhase) {
		case 0: {
			Piece piece = board.Pieces_[x][y];

			if (piece.team_ == currentTeam && currentTeam == thisTeam) {
				if (piece.getName().contains("king")) {
					checkCast();
				}
				piece.getMoves(x, y, board);
				oldX = x;
				oldY = y;
				gamePhase++;
			}
			break;
		}
		case 1: {
			Piece piece = board.Pieces_[x][y];

			if (board.Pieces_[x][y].highlight_) {
				piece = board.Pieces_[oldX][oldY];
				Piece oldPiece = board.Pieces_[x][y];

				board.putPiece(piece, x, y);
				board.removePiece(oldX, oldY);
				board.clearAllHighlights();
				isInCheck();
				if (check) {
					board.putPiece(piece, oldX, oldY);
					board.removePiece(x, y);
					board.putPiece(oldPiece, x, y);
					Toast.makeText(main.getApplicationContext(),
							"You are still in Check", Toast.LENGTH_SHORT)
							.show();
					board.clearAllHighlights();
					adapter_.notifyDataSetChanged();
					gamePhase--;
					return;
				}

				if (piece.getName().contains("pawn")
						&& oldPiece.team_ == Board.NoTeam && oldX != x
						&& piece.team_ == Board.Team1) {
					board.removePiece(x, y - 1);
				} else if (piece.getName().contains("pawn")
						&& oldPiece.team_ == Board.NoTeam && oldX != x
						&& piece.team_ == Board.Team2) {
					board.removePiece(x, y + 1);
				} else if (piece.getName() == "rook1") {
					board.team1QueenSideCast = false;
				} else if (piece.getName() == "rook2") {
					board.team1KingSideCast = false;
				} else if (piece.getName() == "rook3") {
					board.team2QueenSideCast = false;
				} else if (piece.getName() == "rook4") {
					board.team2KingSideCast = false;
				} else if (piece.getName() == "king1") {
					if (oldX - x == 2) {
						oldPiece = board.Pieces_[0][0];
						board.removePiece(0, 0);
						board.putPiece(oldPiece, x + 1, y);
					} else if (oldX - x == -2) {
						oldPiece = board.Pieces_[7][0];
						board.removePiece(7, 0);
						board.putPiece(oldPiece, x - 1, y);
					}
					board.team1QueenSideCast = false;
					board.team1KingSideCast = false;
				} else if (piece.getName() == "king2") {
					if (oldX - x == 2) {
						oldPiece = board.Pieces_[0][7];
						board.removePiece(0, 7);
						board.putPiece(oldPiece, x + 1, y);
					} else if (oldX - x == -2) {
						oldPiece = board.Pieces_[7][7];
						board.removePiece(7, 7);
						board.putPiece(oldPiece, x - 1, y);
					}
					board.team2QueenSideCast = false;
					board.team2KingSideCast = false;
				}
				gamePhase++;
				putInCheck();
				isCheckmate();
				if (checkmate) {
					Toast.makeText(main.getApplicationContext(), "Checkmate",
							Toast.LENGTH_SHORT).show();
				}
				if (Math.abs(y - oldY) == 2 && piece.getName().contains("pawn")) {
					board.enPass = x;
				} else
					board.enPass = -1;

				checkPromotion();
				Toast.makeText(main.getApplicationContext(),
						"You can now submit your move.", Toast.LENGTH_SHORT)
						.show();

			} else if (board.Pieces_[x][y].team_ == currentTeam) {
				board.clearAllHighlights();
				piece = board.Pieces_[x][y];
				piece.getMoves(x, y, board);
				oldX = x;
				oldY = y;
			} else {
				board.clearAllHighlights();
				gamePhase--;
			}

			if (check) {
				Toast.makeText(main.getApplicationContext(), "Check",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		case 2: {
			break;
		}

		}
		adapter_.notifyDataSetChanged();

	}

	private void checkCast() {
		ChessBoard board = (ChessBoard) this.getBoard();
		boolean flag1 = true;
		boolean flag2 = true;
		if (currentTeam == Board.Team1) {
			if (board.team1QueenSideCast) {
				for (int i = 2; i <= 3; i++) {
					if (board.Pieces_[i][0].team_ != Board.NoTeam) {
						flag1 = false;
						break;
					}
				}
				out: for (int i = 0; i < board.width_; i++) {
					for (int j = 0; j < board.length_; j++) {
						Piece temp = board.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board.clearAllHighlights();

							temp.getMoves(i, j, board);
							if (board.Pieces_[2][0].highlight_
									|| board.Pieces_[3][0].highlight_
									|| board.Pieces_[4][0].highlight_) {
								flag1 = false;
								break out;
							}
						}

					}
				}
			} else
				flag1 = false;

			if (board.team1KingSideCast) {
				for (int i = 5; i <= 6; i++) {
					if (board.Pieces_[i][0].team_ != Board.NoTeam) {
						flag2 = false;
						break;
					}
				}
				out: for (int i = 0; i < board.width_; i++) {
					for (int j = 0; j < board.length_; j++) {
						Piece temp = board.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board.clearAllHighlights();
							temp.getMoves(i, j, board);
							if (board.Pieces_[6][0].highlight_
									|| board.Pieces_[5][0].highlight_
									|| board.Pieces_[4][0].highlight_) {
								flag2 = false;
								break out;
							}
						}

					}
				}
			} else
				flag2 = false;
			board.clearAllHighlights();
			board.Pieces_[2][0].setHighlighted(flag1);
			board.Pieces_[6][0].setHighlighted(flag2);
		} else if (currentTeam == Board.Team2) {
			if (board.team2QueenSideCast) {
				for (int i = 2; i <= 3; i++) {
					if (board.Pieces_[i][7].team_ != Board.NoTeam) {
						flag1 = false;
						break;
					}
				}
				out: for (int i = 0; i < board.width_; i++) {
					for (int j = 0; j < board.length_; j++) {
						Piece temp = board.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board.clearAllHighlights();
							temp.getMoves(i, j, board);
							if (board.Pieces_[2][7].highlight_
									|| board.Pieces_[3][7].highlight_
									|| board.Pieces_[4][7].highlight_) {
								flag1 = false;
								break out;
							}
						}

					}
				}
			} else
				flag1 = false;

			if (board.team2KingSideCast) {
				for (int i = 5; i <= 6; i++) {
					if (board.Pieces_[i][7].team_ != Board.NoTeam) {
						flag2 = false;
						break;
					}
				}
				out: for (int i = 0; i < board.width_; i++) {
					for (int j = 0; j < board.length_; j++) {
						Piece temp = board.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board.clearAllHighlights();
							temp.getMoves(i, j, board);
							if (board.Pieces_[6][7].highlight_
									|| board.Pieces_[5][7].highlight_
									|| board.Pieces_[4][7].highlight_) {
								flag2 = false;
								break out;
							}
						}

					}
				}
			} else
				flag2 = false;
			board.clearAllHighlights();
			board.Pieces_[2][7].setHighlighted(flag1);
			board.Pieces_[6][7].setHighlighted(flag2);
		}
	}

	private void checkPromotion() {
		final ChessBoard board = (ChessBoard) this.getBoard();
		for (int i = 0; i < 8; i++) {
			Piece pawn;
			pawn = board.Pieces_[i][7];
			final int x = i;

			if (pawn.getName().contains("pawn")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(main);
				builder.setTitle("Pick a replacement piece");
				OnClickListener listener = new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Queen replace = new Queen(Board.Team1, "queen3");
							board.putPiece(replace, x, 7);
						} else if (which == 1) {
							Knight replace = new Knight(Board.Team1, "knight5");
							board.putPiece(replace, x, 7);
						} else if (which == 2) {
							Bishop replace = new Bishop(Board.Team1, "bishop5");
							board.putPiece(replace, x, 7);
						} else if (which == 3) {
							Rook replace = new Rook(Board.Team1, "rook5");
							board.putPiece(replace, x, 7);
						}
						adapter_.notifyDataSetChanged();
					}
				};
				CharSequence cs[] = { "Queen", "Knight", "Bishop", "Rook" };
				builder.setItems(cs, listener);
				AlertDialog dialog = builder.create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}
		}
		for (int i = 0; i < 8; i++) {
			Piece pawn;
			pawn = board.Pieces_[i][0];
			final int x = i;

			if (pawn.getName().contains("pawn")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(main);
				builder.setTitle("Pick a replacement piece");
				OnClickListener listener = new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Queen replace = new Queen(Board.Team2, "queen3");
							board.putPiece(replace, x, 0);
						} else if (which == 1) {
							Knight replace = new Knight(Board.Team2, "knight5");
							board.putPiece(replace, x, 0);
						} else if (which == 2) {
							Bishop replace = new Bishop(Board.Team2, "bishop5");
							board.putPiece(replace, x, 0);
						} else if (which == 3) {
							Rook replace = new Rook(Board.Team2, "rook5");
							board.putPiece(replace, x, 0);
						}
						adapter_.notifyDataSetChanged();
					}
				};
				CharSequence cs[] = { "Queen", "Knight", "Bishop", "Rook" };
				builder.setItems(cs, listener);
				AlertDialog dialog = builder.create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}
		}

	}

	@Override
	public void submitClick() {
		super.submitClick();

	}

	public void putInCheck() {
		ChessBoard board = (ChessBoard) this.getBoard();
		Piece king;

		int coord[] = new int[2];
		if (currentTeam == -1) {
			king = board.getPiece("king1", coord);
		} else
			king = board.getPiece("king2", coord);

		for (int i = 0; i < board.width_; i++) {
			for (int j = 0; j < board.length_; j++) {
				Piece temp = board.Pieces_[i][j];
				if (temp.team_ == currentTeam) {
					board.clearAllHighlights();
					temp.getMoves(i, j, board);
					if (king.highlight_) {
						check = true;
					}
				}

			}
		}
		board.clearAllHighlights();
	}

	public void isCheckmate() {
		ChessBoard board = (ChessBoard) this.getBoard();
		if (!check) {
			return;
		}
		boolean[][] possibleMoves = new boolean[board.width_][board.length_];
		Piece king;

		int coord[] = new int[2];
		if (currentTeam == -1) {
			king = board.getPiece("king1", coord);
		} else
			king = board.getPiece("king2", coord);

		board.removePiece(coord[0], coord[1]);
		for (int i = 0; i < board.width_; i++) {
			for (int j = 0; j < board.length_; j++) {
				Piece temp = board.Pieces_[i][j];
				if (temp.team_ == currentTeam) {
					board.clearAllHighlights();
					temp.getMoves(i, j, board);
					if (temp.getClass() == Pawn.class) {
						for (int k = 0; k < board.length_; k++) {
							board.Pieces_[i][k].highlight_ = false;
						}
					}
					for (int m = 0; m < board.width_; m++) {
						for (int n = 0; n < board.length_; n++) {
							if (board.Pieces_[m][n].highlight_) {
								possibleMoves[m][n] = true;
							}
						}
					}
				}

			}
		}
		board.clearAllHighlights();

		board.putPiece(king, coord[0], coord[1]);
		king.getMoves(coord[0], coord[1], board);
		for (int m = 0; m < board.width_; m++) {
			for (int n = 0; n < board.length_; n++) {
				if (board.Pieces_[m][n].highlight_)
					if (possibleMoves[m][n] == false) {
						checkmate = false;
						board.clearAllHighlights();
						return;
					}

			}
		}
		board.clearAllHighlights();

		for (int i = 0; i < board.width_; i++) {
			for (int j = 0; j < board.length_; j++) {
				Piece temp = board.Pieces_[i][j];
				if (temp.team_ == -currentTeam) {
					board.clearAllHighlights();
					temp.getMoves(i, j, board);
					for (int m = 0; m < board.width_; m++) {
						for (int n = 0; n < board.length_; n++) {
							Piece piece = board.Pieces_[i][j];
							if (piece.getName() == "king1"
									|| piece.getName() == "king2") {
								continue;
							}
							Piece oldPiece = board.Pieces_[m][n];
							if (board.Pieces_[m][n].highlight_
									&& possibleMoves[m][n] == true) {
								board.putPiece(piece, m, n);
								board.removePiece(i, j);
								board.clearAllHighlights();
								isInCheck();
								if (!check) {
									Log.d("Hussain", "" + "(" + m + "," + n
											+ ")");
									checkmate = false;
									board.putPiece(piece, i, j);
									board.removePiece(m, n);
									board.putPiece(oldPiece, m, n);
									board.clearAllHighlights();
									return;
								}
							}

						}
					}
				}

			}

		}

		checkmate = true;
		board.clearAllHighlights();
	}

	public void isInCheck() {
		ChessBoard board = (ChessBoard) this.getBoard();
		Piece king;

		int coord[] = new int[2];
		if (currentTeam == 1) {
			king = board.getPiece("king1", coord);
		} else
			king = board.getPiece("king2", coord);

		for (int i = 0; i < board.width_; i++) {
			for (int j = 0; j < board.length_; j++) {
				Piece temp = board.Pieces_[i][j];
				if (temp.team_ == -currentTeam) {
					int tempcoord[] = new int[2];
					board.getPiece(temp.getName(), tempcoord);
					board.clearAllHighlights();
					temp.getMoves(tempcoord[0], tempcoord[1], board);
					if (king.highlight_) {
						check = true;
						board.clearAllHighlights();
						return;
					}
				}
			}
		}
		check = false;
		board.clearAllHighlights();
	}

}
