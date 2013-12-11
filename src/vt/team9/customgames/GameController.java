package vt.team9.customgames;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.vt.boardgames.MainActivity;
import edu.vt.boardgames.R;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsServer;
import edu.vt.boardgames.network.response.HandlerResponse;

public abstract class GameController extends Object {
	PiecesAdapter adapter_;
	ProgressDialog progress;
	private Board board_;
	public Game game_;
	Button submit_;
	Button reset_;
	boolean moved = false;
	int old_team;
	int gamePhase = 0;
	int currentTeam = 1;
	int oldX;
	int oldY;
	int thisTeam = 1;
	boolean timerFlag = false;
	CountDownTimer countdown;
	int id_ = -1;
	User thisUser;

	public GameController(PiecesAdapter adapter, Board board, Button submit,
			Button reset) {
		adapter_ = adapter;
		board_ = board;
		submit_ = submit;
		reset_ = reset;
		board.initBoard();
		submit_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitClick();
			}
		});
		reset_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UtilsServer.getGameFromServer(handler, id_);
				gamePhase=0;
			}
		});
		countdown = new CountDownTimer(30000, 1000) {

			public void onTick(long millisUntilFinished) {
				// mTextField.setText("seconds remaining: " +
				// millisUntilFinished / 1000);
			}

			public void onFinish() {
				UtilsServer.getGameFromServer(handler, id_);
			}
		};

	}

	public void submitClick() {
		// save the game state here.
		if (gamePhase == 2) {
			gamePhase = 0;
			moved = false;
			board_.clearAllHighlights();
			currentTeam = -currentTeam;
			game_.setBoard(board_);
			game_.setTurn(currentTeam);
			UtilsServer.submitNewGameState(handler_, game_);
			timerFlag = true;
			countdown.start();
		}
	}

	private HandlerResponse<String> handler_ = new HandlerResponse<String>() {
		public void onResponseArrayObj(java.util.ArrayList<String> response) {
			Toast.makeText(submit_.getContext(),
					"Move submitted. " + response.get(0), Toast.LENGTH_SHORT)
					.show();
		};
	};

	abstract void itemClicked(int position) throws InstantiationException,
			IllegalAccessException;

	public void setGame(Game game) {
		game_ = game;
		if (game.getTurn() == thisTeam) {
			countdown.cancel();
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					submit_.getContext()).setSmallIcon(R.drawable.icon_chess)
					.setContentTitle("Chess").setContentText("It's your turn on game: " + id_ + "!");
			Intent mainIntent = new Intent(submit_.getContext(),
					MainActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(submit_
					.getContext());
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(mainIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
					0, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);
			submit_.getContext();
			NotificationManager mNotificationManager = (NotificationManager) submit_
					.getContext()
					.getSystemService(Context.NOTIFICATION_SERVICE);
			int mID = 0;
			mNotificationManager.notify(mID, mBuilder.build());
		} else
			countdown.start();

		if (game.getTurn() != 0)
			currentTeam = game.getTurn();

		Board board = game.getBoard();
		if (board != null) {
			board_ = board;
			adapter_.setBoardToDraw(board_);
			adapter_.notifyDataSetChanged();
		}
	}

	public void setThisTeam(int thisTeam) {
		this.thisTeam = thisTeam;
	}

	public Board getBoard() {
		return board_;
	}

	public void setBoard(Board board) {
		board_ = board;
	}

	// @SuppressWarnings("unchecked")
	public void retrieveGame(Bundle bundle) {
		id_ = bundle.getInt("id");
		thisUser = new User(bundle.getString("username"));
		thisUser.setId(bundle.getInt("userid"));
		// ArrayList<String> usernames = (ArrayList<String>)
		// bundle.getSerializable("usernames");
		UtilsServer.getGameFromServer(handler, id_);
		progress = ProgressDialog.show(submit_.getContext(), "Wait!",
				"Retrieving your game.", true, false);

	}

	private HandlerResponse<Game> handler = new HandlerResponse<Game>() {
		public void onResponseArrayObj(java.util.ArrayList<Game> response) {

			progress.dismiss();

			if (response != null && response.size() > 0) {
				setGame(response.get(0));
			}
		};
	};
}
