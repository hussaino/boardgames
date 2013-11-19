package vt.team9.boardgames;

import vt.team9.boardgame.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RandomList extends ListFragment{
	
	String[] list_contents = {
		"item 1",
		"item 2",
		"item 3",
		"item 4",
		"item 5"
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.list, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_contents));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		Fragment newFragment = new MainView();
		FragmentManager fm = getFragmentManager();
		switch (position){
		case 0:
			newFragment = new Item1();
			break;
		case 1:
			newFragment = new Item2();
			break;
		case 2:
			newFragment = new Item3();
			break;
		case 3:
			newFragment = new Item4();
			break;
		case 4:
			newFragment = new Item5();
			break;
		}
		if (newFragment != null)
			switchContent(newFragment);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
	
	private void switchContent(Fragment fragment){
		MainActivity ma = (MainActivity)getActivity();
		ma.switchContent(fragment);
	}

}
