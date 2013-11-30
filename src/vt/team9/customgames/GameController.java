package vt.team9.customgames;

import org.json.JSONException;
import org.json.JSONObject;

import edu.vt.boardgames.network.UtilsJSON;
import edu.vt.boardgames.network.UtilsServer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
