package edu.vt.boardgames;

import vt.team9.customgames.ChessBoard;
import vt.team9.customgames.ChessController;
import vt.team9.customgames.PiecesAdapter;
import vt.team9.customgames.SpaceListener;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class ChessGame extends Fragment {

	public ChessGame() {

	}

	int numOfColumns = 8;
	private ChessController controller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.game_main, container, false);
		GridView grid = (GridView) rootView.findViewById(R.id.Grid);
		LinearLayout ll = (LinearLayout) rootView
				.findViewById(R.id.LinearLayout1);
		int width = ll.getWidth();

		ChessBoard board = new ChessBoard(8, 8);
		Button button = (Button) rootView.findViewById(R.id.submitButton);
		ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
		layoutParams.height = width;
		grid.setLayoutParams(layoutParams);
		grid.setNumColumns(numOfColumns);
		grid.setColumnWidth(grid.getWidth() / numOfColumns);
		grid.setBackgroundResource(R.drawable.checkered_background);
		PiecesAdapter adapter = new PiecesAdapter(this.getActivity()
				.getApplicationContext(), board);
		controller = new ChessController(adapter, board, button,
				(Activity) this.getActivity());
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new SpaceListener(controller));
		controller.retrieveGame(this.getArguments());
		return rootView;
	}
}
