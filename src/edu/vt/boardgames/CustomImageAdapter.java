package edu.vt.boardgames;

import java.util.ArrayList;

import edu.vt.boardgames.R;
import edu.vt.boardgames.network.Game;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomImageAdapter extends ArrayAdapter<Item> {
	Context context;
	int layoutResourceId;
	LinearLayout linearMain;
	ArrayList<Game> data = new ArrayList<Game>();

	public CustomImageAdapter(Context context, int layoutResourceId,
			ArrayList<Game> data) {
		super(context, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			linearMain = (LinearLayout) row.findViewById(R.id.lineraMain);

			Game game = data.get(position);
			for (int j = 0; j < data.size(); j++) {
				TextView label = new TextView(context);
				label.setText(game.getId());
				linearMain.addView(label);
			}
			ImageView image = new ImageView(context);
			//int outImage = myImage.image;
			//image.setImageResource(outImage);
			linearMain.addView(image);
		}

		return row;

	}

}
