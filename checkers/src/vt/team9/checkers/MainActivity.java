package vt.team9.checkers;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	static final int numOfColumns = 8;
	static final int numOfRows = 8;
	private static Context context_;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView grid = (GridView)findViewById(R.id.Grid);
		LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout1);
		int width = ll.getWidth();
		Board board = new ChessBoard(8,8);
		Button button = (Button) findViewById(R.id.submitButton);
		ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
		layoutParams.height = width;
		grid.setLayoutParams(layoutParams);
		grid.setNumColumns(numOfColumns);
		grid.setColumnWidth(grid.getWidth()/numOfColumns);
		grid.setBackgroundResource(R.drawable.checkered_background);
		PiecesAdapter adapter = new PiecesAdapter(this,board);
		GameController controller = new GameController(adapter, board, button);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new SpaceListener(controller));
		MainActivity.context_ = getApplicationContext();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public static Context getAppContext()
	{
		return context_;
	}

}
