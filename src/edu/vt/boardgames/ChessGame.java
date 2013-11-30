package edu.vt.boardgames;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import vt.team9.customgames.ChessActivity;
import vt.team9.customgames.ChessBoard;
import vt.team9.customgames.ChessController;
import vt.team9.customgames.PiecesAdapter;
import vt.team9.customgames.SpaceListener;
import edu.vt.boardgames.R;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.UtilsJSON;
import edu.vt.boardgames.network.UtilsServer;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class ChessGame extends Fragment {
	
	public ChessGame(){}
	int numOfColumns = 8;
	Game game_;
	ProgressDialog progress;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		View rootView = inflater.inflate(R.layout.game_main, container, false);
		GridView grid = (GridView)rootView.findViewById(R.id.Grid);
		LinearLayout ll = (LinearLayout)rootView.findViewById(R.id.LinearLayout1);
		int width = ll.getWidth();
		//Game game = new Game(true, false,5,2,1,-1,-1);
		//Game game = UtilsServer.getGame(id);
		UtilsServer.getGamesFromServer(handler, KEY_MSG_TYPE, MSG_TYPE_RESPONSE_GET_GAME, KEY_RESPONSE);
		
		ChessBoard board = new ChessBoard(8,8);
		Button button = (Button) rootView.findViewById(R.id.submitButton);
		ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
		layoutParams.height = width;
		grid.setLayoutParams(layoutParams);
		grid.setNumColumns(numOfColumns);
		grid.setColumnWidth(grid.getWidth()/numOfColumns);
		grid.setBackgroundResource(R.drawable.checkered_background);
		PiecesAdapter adapter = new PiecesAdapter(this.getActivity().getApplicationContext(),board);
		ChessController controller = new ChessController(adapter, board, button,(Activity)this.getActivity());
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new SpaceListener(controller));
		//ChessActivity.context_ = getActivity().getApplicationContext();
		progress = new ProgressDialog(this.getActivity());
		progress.setMessage("Creating new Game");
		progress.show();
		return rootView;
    }
	

	private static final String KEY_RESPONSE = "response_key";
	private static final String KEY_MSG_TYPE = "msg_Type";
	private static final short MSG_TYPE_RESPONSE_GET_GAME = 1;
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			progress.dismiss();

			Bundle msgBundle = msg.getData();

			switch (msgBundle.getShort(KEY_MSG_TYPE))
			{
			case MSG_TYPE_RESPONSE_GET_GAME:
				ArrayList<Game> getResponseGames = (ArrayList<Game>) msgBundle
				.getSerializable(KEY_RESPONSE);
				if (getResponseGames != null)
				{
					game_ = getResponseGames.get(0);
					Log.d("Hussain",game_.toString());
				}
				break;
			}
		}
	};
}
