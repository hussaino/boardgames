package vt.team9.checkers;

import android.app.Activity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	static final int numOfColumns = 8;
	static final int numOfRows = 8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView grid = (GridView)findViewById(R.id.Grid);
		LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout1);
		int width = ll.getWidth();
		ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
		layoutParams.height = width;
		grid.setLayoutParams(layoutParams);
		grid.setNumColumns(numOfColumns);
		grid.setColumnWidth(grid.getWidth()/numOfColumns);
		grid.setBackgroundResource(R.drawable.checkered_background);
		grid.setAdapter(new PiecesAdapter(this));

		grid.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
		grid.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
