package vt.team9.checkers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SpaceListener implements OnItemClickListener{

	GameController controller_;
	
	public SpaceListener(GameController controller){
		controller_ = controller;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long ID) {
		try {
			controller_.itemClicked(position);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
