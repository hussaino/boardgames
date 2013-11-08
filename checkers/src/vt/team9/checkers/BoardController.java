package vt.team9.checkers;

public class BoardController extends Object {

	PiecesAdapter adapter_;
	Board board_;
	int old_position_x;
	int old_position_y;
	int old_team;
	public BoardController(PiecesAdapter adapter,Board board) {
		adapter_ = adapter;
		board_ = board;
		int team1_x[] = {0,2,4,6,1,3,5,7,0,2,4,6};
		int team1_y[] = {0,0,0,0,1,1,1,1,2,2,2,2};
		board.initPiecesTeam1(team1_x,team1_y);
		int team2_x[] = {1,3,5,7,0,2,4,6,1,3,5,7};
		int team2_y[] = {5,5,5,5,6,6,6,6,7,7,7,7};
		board.initPiecesTeam2(team2_x,team2_y);
		// TODO Auto-generated constructor stub
	}
	void itemClicked(int position){
		int x = position %8;
		int y = position /8;
		if(board_.highlighted[x][y]){
			if(old_team == 1){
				board_.putTeam1Piece(x,y);
				board_.removeTeam1Piece(old_position_x,old_position_y);
				board_.clearAll();
			}
			else{
				board_.putTeam2Piece(x,y);
				board_.removeTeam2Piece(old_position_x,old_position_y);
				board_.clearAll();
			}
		}
		else if(board_.containsTeam1Piece[x][y]){
			board_.clearAll();
			int j_x = x+1;
			int j_y = y+1;
			board_.highlightSpace(j_x,j_y);
			j_x = x-1;
			board_.highlightSpace(j_x,j_y);
			old_team = 1;
			old_position_x = x;
			old_position_y = y;
		}
		else if(board_.containsTeam2Piece[x][y]){
			board_.clearAll();
			int j_x = x+1;
			int j_y = y-1;
			board_.highlightSpace(j_x,j_y);
			j_x = x-1;
			board_.highlightSpace(j_x,j_y);
			old_team = 2;
			old_position_x = x;
			old_position_y = y;
		}
		adapter_.notifyDataSetChanged();
	}

	
}
