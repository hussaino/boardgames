package edu.vt.boardgames;

import java.util.ArrayList;

import edu.vt.boardgames.R;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.User;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomImageAdapter extends BaseAdapter {
	Context context;
	int layoutResourceId;
	LinearLayout linearMain;
	ArrayList<Game> data = new ArrayList<Game>();

	public CustomImageAdapter(Context context, int layoutResourceId,
			ArrayList<Game> data) {
		super();
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowLayout = LayoutInflater.from(context).inflate(
				R.layout.list, null);

		Game game = data.get(position);

		TextView id = (TextView) rowLayout
				.findViewById(R.id.TextView_IdInListView);
		id.setText("" + game.getId());

		LinearLayout linLayout = (LinearLayout) rowLayout
				.findViewById(R.id.LinearLayout_PlayersInListView);

		for (User user : game.getPlayers()) {
			TextView player = new TextView(context);
			player.setTextColor(context.getResources().getColor(R.color.black));
			player.setTextSize(10);
			player.setText(user.getName());
			player.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			linLayout.addView(player);
		}

		return rowLayout;

	}

}
