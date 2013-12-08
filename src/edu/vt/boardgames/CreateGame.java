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

	public CreateGame() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.creategame, container, false);
		edittext = (EditText) rootView.findViewById(R.id.opponent);
		Button create = (Button) rootView.findViewById(R.id.btn_create);
		final Bundle bundle = new Bundle(this.getArguments());
		create.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				String[] opponents = edittext.getText().toString().split(",");
				bundle.putSerializable("opponenets", opponents);
				if (opponents[0] == "") {
					Toast.makeText(getActivity(), "Invalid opponent",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//Fragment chessgame = ChessGame.instantiate(getActivity(),"Chess");
				//chessgame.setArguments(bundle);
				//ft.replace(R.layout.game_main, chessgame);
				//ft.commit();
				//ft.addToBackStack(null);
			}
		});
		return rootView;
	}

}
