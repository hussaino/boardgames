package edu.vt.boardgames;

import vt.team9.customgames.Board;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PiecesAdapter extends BaseAdapter {

	Context context_;
	int width_;
	Board board_;

	public PiecesAdapter(Context applicationContext, Board board) {
		context_ = applicationContext;
		board_ = board;
	}

	@Override
	public int getCount() {
		return 64;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		int x = position % 8;
		int y = position / 8;
		int width = context_.getResources().getDisplayMetrics().widthPixels;
		int boxWidth = width / 8;
		int imageWidth = (int) 95 * boxWidth / 100;
		int imagePad = (int) 10 * boxWidth / 100;
		// Log.d("Hussain","" +(int) 80*boxWidth/100);
		ViewGroup.LayoutParams params = new GridView.LayoutParams(imageWidth,
				imageWidth);
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(context_);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(imagePad, imagePad, imagePad, imagePad);

		} else {
			imageView = (ImageView) convertView;
		}
		if (board_.Pieces_[x][y].highlight_) {
			imageView
					.setImageResource(board_.Pieces_[x][y].getHighlightImage());
		} else if (board_.Pieces_[x][y].team_ == Board.Team1) {
			imageView.setImageResource(board_.Pieces_[x][y].getTeam1Image());
		} else if (board_.Pieces_[x][y].team_ == Board.Team2) {
			imageView.setImageResource(board_.Pieces_[x][y].getTeam2Image());
		}

		else
			imageView.setImageResource(0);
		return imageView;
	}

	public void setBoardToDraw(Board board) {
		this.board_ = board;
	}

}
