package vt.team9.customgames;

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
		
		Toast.makeText(submit_.getContext(), "Move submitted.", Toast.LENGTH_SHORT).show();
		}

	}

	void itemClicked(int position) throws InstantiationException,
			IllegalAccessException {
	}
}
