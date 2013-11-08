package vt.team9.checkers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SpaceListener implements OnItemClickListener{

	BoardController controller_;
	
	public SpaceListener(BoardController controller){
		controller_ = controller;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long ID) {
		controller_.itemClicked(position);
	}
	
}
