package edu.vt.boardgames;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGame extends Fragment {
	EditText edittext;
	public CreateGame(){
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.creategame, container, false);
		//LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.creatgame_linearlayout);
		edittext = (EditText) rootView.findViewById(R.id.opponent);
		Button create = (Button) rootView.findViewById(R.id.btn_create);
		final String[] opponents = edittext.getText().toString().split(",");
		//usernames[0] = savedInstanceState.getString("username");
		//usernames[1] = edittext.getText().toString();
		final Bundle bundle = new Bundle();
		//bundle.putString("username", savedInstanceState.getString("username"));
		bundle.putSerializable("opponenets", opponents);
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final FragmentTransaction ft = getFragmentManager().beginTransaction();
				if(opponents[0] == ""){
					Toast.makeText(getActivity(), "Invalid opponent", Toast.LENGTH_SHORT).show();
				}
				Fragment chessgame = new ChessGame();
				chessgame.setArguments(bundle);
				ft.replace(R.layout.game_main, chessgame, "Chess");
				ft.commit();				
			}
		});
		return rootView;
	}

}
