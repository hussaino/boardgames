package edu.vt.boardgames;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CustomListView extends DialogFragment {
	ArrayList<Item> imageArry = new ArrayList<Item>();
	CustomImageAdapter adapter;

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.main);
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.listview, container, false);

		// add image and text in arraylist
		String Video[] = { "Chess Game", "User: test", "Score: .." };
		String Song[] = { "Checkers Game", "User: test", "Score: .." };
		String Movi[] = { "TicTacToe", "User: test", "Score: .." };

		imageArry.add(new Item(R.drawable.checkers_icon, Song));
		imageArry.add(new Item(R.drawable.icon_chess, Video));
		imageArry.add(new Item(R.drawable.btn_login, Movi));
		// imageArry.add(new Item(checkers_icon, name))
		// add data in contact image adapter
		// adapter = new CustomImageAdapter(this.getActivity(), R.layout.list,
		// imageArry);
		ListView dataList = (ListView) rootView.findViewById(R.id.list);
		dataList.setAdapter(adapter);
		return rootView;

	}

}
