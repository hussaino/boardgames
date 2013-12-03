package vt.team9.customgames;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
		}
	}

	private Handler handler_ = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle msgBundle = msg.getData();
			String putNewStateResponse = msgBundle.getString("response");
			Toast.makeText(submit_.getContext(), "Move submitted. " + putNewStateResponse,
					Toast.LENGTH_SHORT).show();
		}
	};

	abstract void itemClicked(int position) throws InstantiationException, IllegalAccessException;

	public void setGame(Game game)
	{
		game_ = game;
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
	public void retrieveGame(Bundle bundle){
		int id = bundle.getInt("id");
		String[] username = bundle.getStringArray("usernames");
		if(id == -1){
			UtilsServer.createNewGame(handler, true, false, 5, 2, 1 ,-1 ,-1);
			progress = ProgressDialog.show(submit_.getContext(), "Wait!", "Creating your game.", true, false);
		}
		else{
			UtilsServer.getGameFromServer(handler, id);
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
						//Log.d("Hussain", controller.game_.toString());
					}
				};
			};
}
