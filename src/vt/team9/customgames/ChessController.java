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
	ChessBoard board_;
	public ChessController(PiecesAdapter adapter, Board board, Button submit, Activity activity) {
		super(adapter, board, submit);
		board_ = (ChessBoard) board;
		// TODO Auto-generated constructor stub
		checkmate = false;
		check = false;
		main = activity;
	}
	@Override
	void itemClicked(int position) throws InstantiationException,
			IllegalAccessException {
		int x = position % 8;
		int y = position / 8;
		switch (gamePhase) {
		case 0: {
			Piece piece = board_.Pieces_[x][y];

			if(piece.getName().contains("king")){
				checkCast();
			}
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
					board_.putPiece(piece, oldX, oldY);
					board_.removePiece(x, y);
					board_.putPiece(oldPiece, x, y);
					Toast.makeText(main.getApplicationContext(), "You are still in Check", Toast.LENGTH_SHORT).show();
					board_.clearAllHighlights();
					adapter_.notifyDataSetChanged();
					gamePhase--;
					return;
				}
				
				if(piece.getName().contains("pawn") && oldPiece.team_ == Board.NoTeam && oldX != x && piece.team_ == Board.Team1){
					board_.removePiece(x, y-1);
				}
				else if(piece.getName().contains("pawn") && oldPiece.team_ == Board.NoTeam && oldX != x && piece.team_ == Board.Team2){
					board_.removePiece(x, y+1);
				}
				else if(piece.getName() == "rook1"){
					board_.team1QueenSideCast = false;
				}
				else if(piece.getName() == "rook2"){
					board_.team1KingSideCast = false;
				}
				else if(piece.getName() == "rook3"){
					board_.team2QueenSideCast = false;
				}
				else if(piece.getName() == "rook4"){
					board_.team2KingSideCast = false;
				}
				else if(piece.getName() == "king1"){
					if(oldX - x == 2){
						oldPiece = board_.Pieces_[0][0];
						board_.removePiece(0, 0);
						board_.putPiece(oldPiece, x+1, y);
					}
					else if(oldX - x == -2){
						oldPiece = board_.Pieces_[7][0];
						board_.removePiece(7, 0);
						board_.putPiece(oldPiece, x-1, y);
					}
					board_.team1QueenSideCast = false;
					board_.team1KingSideCast = false;
				}
				else if(piece.getName() == "king2"){
					if(oldX - x == 2){
						oldPiece = board_.Pieces_[0][7];
						board_.removePiece(0, 7);
						board_.putPiece(oldPiece, x+1, y);
					}
					else if(oldX - x == -2){
						oldPiece = board_.Pieces_[7][7];
						board_.removePiece(7, 7);
						board_.putPiece(oldPiece, x-1, y);
					}
					board_.team2QueenSideCast = false;
					board_.team2KingSideCast = false;
				}
				gamePhase++;
				putInCheck();
				isCheckmate();
				if(checkmate){
					Toast.makeText(main.getApplicationContext(), "Checkmate", Toast.LENGTH_SHORT).show();
				}
				if(Math.abs(y-oldY) == 2 && piece.getName().contains("pawn")){
					board_.enPass = x;
					Log.d("Hussain","here");
				}
				else
					board_.enPass = -1;
				
				checkPromotion();
				Toast.makeText(main.getApplicationContext(), "You can now submit your move.", Toast.LENGTH_SHORT).show();
				

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
				Toast.makeText(main.getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
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
		// TODO Auto-generated method stub
		boolean flag1 = true;
		boolean flag2 = true;
		if(currentTeam == Board.Team1){
			if(board_.team1QueenSideCast){
				for(int i=2;i<=3;i++){
					if(board_.Pieces_[i][0].team_ != Board.NoTeam){
						flag1 = false;
						break;
					}
				}
				out:
				for (int i = 0; i < board_.width_; i++) {
					for (int j = 0; j < board_.length_; j++) {
						Piece temp = board_.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board_.clearAllHighlights();
							
							temp.getMoves(i,j, board_);
							if (board_.Pieces_[2][0].highlight_ || board_.Pieces_[3][0].highlight_ || board_.Pieces_[4][0].highlight_) {
								flag1 = false;
								break out;
							}
						}

					}
				}
			}
			else flag1 = false;
			
			if(board_.team1KingSideCast){
				for(int i=5;i<=6;i++){
					if(board_.Pieces_[i][0].team_ != Board.NoTeam){
						flag2 = false;
						break;
					}
				}
				out:
				for (int i = 0; i < board_.width_; i++) {
					for (int j = 0; j < board_.length_; j++) {
						Piece temp = board_.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board_.clearAllHighlights();
							temp.getMoves(i,j, board_);
							if (board_.Pieces_[6][0].highlight_ || board_.Pieces_[5][0].highlight_ || board_.Pieces_[4][0].highlight_) {
								flag2 = false;
								break out;
							}
						}

					}
				}
			}
			else flag2 = false;
			board_.clearAllHighlights();
			board_.Pieces_[2][0].setHighlighted(flag1);
			board_.Pieces_[6][0].setHighlighted(flag2);
		}
		else if(currentTeam == Board.Team2){
			if(board_.team2QueenSideCast){
				for(int i=2;i<=3;i++){
					if(board_.Pieces_[i][7].team_ != Board.NoTeam){
						flag1 = false;
						break;
					}
				}
				out:
				for (int i = 0; i < board_.width_; i++) {
					for (int j = 0; j < board_.length_; j++) {
						Piece temp = board_.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board_.clearAllHighlights();
							temp.getMoves(i, j, board_);
							if (board_.Pieces_[2][7].highlight_ || board_.Pieces_[3][7].highlight_ || board_.Pieces_[4][7].highlight_) {
								flag1 = false;
								break out;
							}
						}

					}
				}
			}
			else flag1 = false;

			if(board_.team2KingSideCast){
				for(int i=5;i<=6;i++){
					if(board_.Pieces_[i][7].team_ != Board.NoTeam){
						flag2 = false;
						break;
					}
				}
				out:
				for (int i = 0; i < board_.width_; i++) {
					for (int j = 0; j < board_.length_; j++) {
						Piece temp = board_.Pieces_[i][j];
						if (temp.team_ == -currentTeam) {
							board_.clearAllHighlights();
							temp.getMoves(i,j, board_);
							if (board_.Pieces_[6][7].highlight_ || board_.Pieces_[5][7].highlight_ || board_.Pieces_[4][7].highlight_) {
								flag2 = false;
								break out;
							}
						}

					}
				}
			}
			else flag2 = false;
			board_.clearAllHighlights();
			board_.Pieces_[2][7].setHighlighted(flag1);
			board_.Pieces_[6][7].setHighlighted(flag2);
		}
	}

	private void checkPromotion() {
		// TODO Auto-generated method stub
		for(int i=0;i<8;i++){
			Piece pawn;
			pawn = board_.Pieces_[i][7];
			final int x = i;
			
			if(pawn.getName().contains("pawn")){
				AlertDialog.Builder builder = new AlertDialog.Builder(main);
				builder.setTitle("Pick a replacement piece");
				OnClickListener listener = new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(which == 0){
							Queen replace = new Queen(Board.Team1,"queen3");
							board_.putPiece(replace, x, 7);
						}
						else if(which == 1){
							Knight replace = new Knight(Board.Team1,"knight5");
							board_.putPiece(replace, x, 7);
						}
						else if(which == 2){
							Bishop replace = new Bishop(Board.Team1,"bishop5");
							board_.putPiece(replace, x, 7);
						}
						else if(which == 3){
							Rook replace = new Rook(Board.Team1,"rook5");
							board_.putPiece(replace, x, 7);
						}
						adapter_.notifyDataSetChanged();
					}
				};
				CharSequence cs[] = {"Queen","Knight","Bishop","Rook"};
				builder.setItems(cs,listener);
				AlertDialog dialog = builder.create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}
		}
		for(int i=0;i<8;i++){
			Piece pawn;
			pawn = board_.Pieces_[i][0];
			final int x = i;
			
			if(pawn.getName().contains("pawn")){
				AlertDialog.Builder builder = new AlertDialog.Builder(main);
				builder.setTitle("Pick a replacement piece");
				OnClickListener listener = new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(which == 0){
							Queen replace = new Queen(Board.Team2,"queen3");
							board_.putPiece(replace, x, 0);
						}
						else if(which == 1){
							Knight replace = new Knight(Board.Team2,"knight5");
							board_.putPiece(replace, x, 0);
						}
						else if(which == 2){
							Bishop replace = new Bishop(Board.Team2,"bishop5");
							board_.putPiece(replace, x, 0);
						}
						else if(which == 3){
							Rook replace = new Rook(Board.Team2,"rook5");
							board_.putPiece(replace, x, 0);
						}
						adapter_.notifyDataSetChanged();
					}
				};
				CharSequence cs[] = {"Queen","Knight","Bishop","Rook"};
				builder.setItems(cs,listener);
				AlertDialog dialog = builder.create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}
		}

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
					board_.clearAllHighlights();
					temp.getMoves(i, j, board_);
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
					board_.clearAllHighlights();
					temp.getMoves(i, j, board_);
					if(temp.getClass() == Pawn.class){
						for(int k=0; k<board_.length_;k++){
							board_.Pieces_[i][k].highlight_ = false;
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
					board_.clearAllHighlights();
					temp.getMoves(i, j, board_);
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


	
	
}
