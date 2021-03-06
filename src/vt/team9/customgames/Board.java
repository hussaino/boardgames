package vt.team9.customgames;

import edu.vt.boardgames.EmptySpace;
import edu.vt.boardgames.Piece;

public class Board extends Object {
	static final int NoTeam = 0;
	public static int Team1 = 1;
	public static int Team2 = -1;
	public Piece Pieces_[][];
	public int width_;
	public int length_;
	public boolean isHighlighted;

	public Board() {
	}

	public Board(int length, int width) {
		width_ = width;
		length_ = length;
		Pieces_ = new Piece[width][length];
	}

	public void initBoard() {
		for (int i = 0; i < width_; i++) {
			for (int j = 0; j < length_; j++) {
				Pieces_[i][j] = new EmptySpace(NoTeam, "");
			}
		}
	}

	public void putPiece(Piece piece, int x, int y) {
		Pieces_[x][y] = piece;
	}

	public void removePiece(int x, int y) {

		Pieces_[x][y] = new EmptySpace(NoTeam, "");
	}

	public void clearAllHighlights() {
		for (int i = 0; i < width_; i++)
			for (int j = 0; j < length_; j++) {
				Pieces_[i][j].highlight_ = false;
			}
	}

	public Piece getPiece(String name, int[] args) {

		for (int i = 0; i < width_; i++) {
			for (int j = 0; j < length_; j++) {
				Piece temp = Pieces_[i][j];
				if (temp.getName() == name) {
					args[0] = i;
					args[1] = j;

					return temp;
				}
			}
		}
		return new EmptySpace(NoTeam, "null");
	}

	public String toString() {
		String output = "";
		for (int i = 0; i < width_; i++) {
			for (int j = 0; j < length_; j++) {
				output += "(" + i + "," + j + ") :" + Pieces_[i][j].name_
						+ "\n";
			}
		}
		return output;
	}
}
