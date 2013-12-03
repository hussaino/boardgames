package vt.team9.customgames;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.UtilsServer;
import edu.vt.boardgames.network.response.HandlerResponse;

public abstract class GameController extends Object
{
	PiecesAdapter adapter_;
	ProgressDialog progress;
	private Board board_;
	public Game game_;
	Button submit_;
	boolean moved = false;
	int old_team;
	int gamePhase = 0;
	int currentTeam = 1;
	int oldX;
	int oldY;
	int thisTeam = -1;
	boolean timerFlag = false;
	int id_ = -1;

	public GameController(PiecesAdapter adapter, Board board, Button submit)
	{
		adapter_ = adapter;
		board_ = board;
		submit_ = submit;
		board.initBoard();
		submit_.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				submitClick();
			}
		});

	}

	public void submitClick()
	{
		// save the game state here.
		if (gamePhase == 2)
		{
			gamePhase = 0;
			moved = false;
			board_.clearAllHighlights();
			currentTeam = -currentTeam;
			game_.setBoard(board_);
			game_.setTurn(currentTeam);
			UtilsServer.submitNewGameState(handler_, game_);
			timerFlag = true;
			timer();
		}
	}
	public void timer(){
		new CountDownTimer(30000, 1000) {
		
			public void onTick(long millisUntilFinished) {
			    //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
			}
			public void onFinish() {
				UtilsServer.getGameFromServer(handler, id_);
			    //mTextField.setText("done!");
				if(timerFlag)
					timer();
			}
		}.start();
	}

	private HandlerResponse<String> handler_ = new HandlerResponse<String>()
	{
		public void onResponseArrayObj(java.util.ArrayList<String> response) {
			Toast.makeText(submit_.getContext(), "Move submitted. " + response.get(0),
					Toast.LENGTH_SHORT).show();
		};
	};

	abstract void itemClicked(int position) throws InstantiationException, IllegalAccessException;

	public void setGame(Game game)
	{
		game_ = game;
		if(game.getTurn() != currentTeam)
			timerFlag = false;
		if (game.getTurn() != 0)
			currentTeam = game.getTurn();

		Board board = game.getBoard();
		if (board != null)
		{
			board_ = board;
			adapter_.setBoardToDraw(board_);
			adapter_.notifyDataSetChanged();
		}
	}
	
	public void setThisTeam(int thisTeam) {
		this.thisTeam = thisTeam;
	}

	public Board getBoard()
	{
		return board_;
	}

	public void setBoard(Board board)
	{
		board_ = board;
	}

	//@SuppressWarnings("unchecked")
	public void retrieveGame(Bundle bundle){
		id_ = bundle.getInt("id");
		//ArrayList<String> usernames = (ArrayList<String>) bundle.getSerializable("usernames");
		if(id_ == -1){
			UtilsServer.createNewGame(handler, true, false, 5, 2, 1 ,-1 ,-1);
			progress = ProgressDialog.show(submit_.getContext(), "Wait!", "Creating your game.", true, false);
		}
		else{
			UtilsServer.getGameFromServer(handler, id_);
			progress = ProgressDialog.show(submit_.getContext(), "Wait!", "Retrieving your game.", true, false);
		}
	}
	private HandlerResponse<Game> handler = new HandlerResponse<Game>()
			{
				public void onResponseArrayObj(java.util.ArrayList<Game> response) {
					
					progress.dismiss();
					
					if (response != null && response.size() > 0)
					{
						setGame(response.get(0));
					}
				};
			};
}
