package edu.vt.boardgames;

import edu.vt.boardgames.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OtherGames extends Fragment {
	
	public OtherGames(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_othergames, container, false);
         
        return rootView;
    }
}
