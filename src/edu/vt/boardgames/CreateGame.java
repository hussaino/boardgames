package edu.vt.boardgames;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CreateGame extends Fragment {

	public CreateGame(){
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.creategame, container, false);
		LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.creatgame_linearlayout);
		return rootView;
	}

}
