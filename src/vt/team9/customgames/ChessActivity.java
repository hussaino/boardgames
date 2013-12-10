package vt.team9.customgames;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import edu.vt.boardgames.R;

public class ChessActivity extends Activity {
	static final int numOfColumns = 8;
	private ChessController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_main);
		GridView grid = (GridView) findViewById(R.id.Grid);
		LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout1);
		int width = ll.getWidth();

		ChessBoard board = new ChessBoard(8, 8);
		Button button = (Button) findViewById(R.id.submitButton);
		ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
		layoutParams.height = width;
		grid.setLayoutParams(layoutParams);
		grid.setNumColumns(numOfColumns);
		grid.setColumnWidth(grid.getWidth() / numOfColumns);
		grid.setBackgroundResource(R.drawable.checkered_background);
		PiecesAdapter adapter = new PiecesAdapter(this.getApplicationContext(),
				board);
		controller = new ChessController(adapter, board, button, this);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new SpaceListener(controller));
		Bundle bundle = new Bundle();
		bundle.putInt("id", -1);
		controller.retrieveGame(bundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
