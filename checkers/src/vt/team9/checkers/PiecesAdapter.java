package vt.team9.checkers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PiecesAdapter extends BaseAdapter {

	Context context_;
	int width_;

	public PiecesAdapter(Context applicationContext) {
		// TODO Auto-generated constructor stub
		context_ = applicationContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		int width = context_.getResources().getDisplayMetrics().widthPixels;
		int boxWidth = width/8;
		int imageWidth = (int) 95*boxWidth/100;
		int imagePad = (int) 10*boxWidth/100;
		Log.d("Hussain","" +(int) 80*boxWidth/100);
		ViewGroup.LayoutParams params = new GridView.LayoutParams(imageWidth,imageWidth);
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context_);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(imagePad,imagePad,imagePad,imagePad);
    		if((position > 23 && position < 40) || (position % 2 == 1 && position %16 < 8) || (position % 2 == 0 && position %16 >= 8)){
    			return imageView;
    		}
        } else {
            imageView = (ImageView) convertView;
        }
        
        imageView.setImageResource(R.drawable.peach_circle);
        if(position > 40)
        	imageView.setImageResource(R.drawable.moccasin_circle);
        return imageView;
	}

}
