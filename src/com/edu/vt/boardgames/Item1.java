package com.edu.vt.boardgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import test.team9.boardgame.R;


public class Item1 extends SherlockFragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//
		//Intent intent = new Intent(container.getContext(),vt.team9.checkers.MainActivity.class);
		//getActivity().startActivity(intent);
		
		return inflater.inflate(R.layout.item1, null);
	}

}
