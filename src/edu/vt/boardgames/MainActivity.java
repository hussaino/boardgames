package edu.vt.boardgames;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsServer;
import edu.vt.boardgames.network.response.HandlerResponse;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	public static User user_;
	ArrayList<Game> listOfGames;
	ProgressDialog progress;
	private int tab;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		user_ = new User("");
		user_.setId(0);
		progress = new ProgressDialog(getApplicationContext());
		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		
		// // Pages
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],
		// navMenuIcons.getResourceId(4, -1)));
		// // What's hot, We will add a counter here
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],
		// navMenuIcons.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(3);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			if (user_.getName() == "") {
				Toast.makeText(getApplicationContext(),
						"You have to login through facebook first",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if(user_.getId() == 0){
				Toast.makeText(getApplicationContext(),
						"Error in the server, try again later",
						Toast.LENGTH_SHORT).show();
				break;
			}
			showNoticeDialog();
			break;

		case 1:
			if (user_.getName() == "") {
				Toast.makeText(getApplicationContext(),
						"You have to login through facebook first",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if(user_.getId() == 0){
				Toast.makeText(getApplicationContext(),
						"Error in the server, try again later",
						Toast.LENGTH_SHORT).show();
				break;
			}
			tab = 1;
			UtilsServer.getAllOpenGames(handler);
			progress = ProgressDialog.show(this, "Wait!",
					"Retrieving open games", true, false);
			break;
		case 2:
			if (user_.getName() == "") {
				Toast.makeText(getApplicationContext(),
						"You have to login through facebook first",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if(user_.getId() == 0){
				Toast.makeText(getApplicationContext(),
						"Error in the server, try again later",
						Toast.LENGTH_SHORT).show();
				break;
			}
			tab = 2;
			UtilsServer.getAllGamesForUser(handler, user_);
			progress = ProgressDialog.show(this, "Wait!",
					"Retrieving your games", true, false);

			Log.d("Hussain","" + user_.getName() + ":" + user_.getId());
			break;

		case 3:
			fragment = new AndroidFacebookConnectActivity();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = getLayoutInflater();

		final View view = inflater.inflate(R.layout.dialog, null);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view)
				// Add action buttons
				.setPositiveButton("Create",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

								UtilsServer.createNewGame(handler2, true,
										false, 5, 2, 1, -1, -1, user_);

							}

						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		builder.create();
		builder.show();
	}

	public void getGames() {
		// Create an instance of the dialog fragment and show it

		navDrawerItems.get(tab).setCount("" +listOfGames.size());
		navDrawerItems.get(tab).setCounterVisibility(true);
		adapter.notifyDataSetChanged();
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();

		final View view = inflater.inflate(R.layout.listview, null);
		ListView listview = (ListView) view.findViewById(R.id.gameslist);
		CustomImageAdapter adapter = new CustomImageAdapter(
				getApplicationContext(), R.id.list, listOfGames);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		builder.setView(view).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		builder.create();

		final AlertDialog ad = builder.show();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UtilsServer.joinGame(handler2, user_, listOfGames.get(position));
				ad.cancel();
			}
		});
	}

	private HandlerResponse<Game> handler = new HandlerResponse<Game>() {
		public void onResponseArrayObj(java.util.ArrayList<Game> response) {

			progress.dismiss();
			Log.d("Hussain","" + response.size());
			if (response != null && response.size() > 0) {
				listOfGames = response;
				getGames();				
				//Log.d("Hussain","Tab: " + navDrawerItems.get(tab).toString());
			}
			else{
				//Toast.makeText(getApplicationContext(), "No games found", Toast.LENGTH_SHORT).show();
			}
		};
	};

	private HandlerResponse<Game> handler2 = new HandlerResponse<Game>() {
		public void onResponseArrayObj(java.util.ArrayList<Game> response) {

			progress.dismiss();
			if (response != null && response.size() > 0) {
				Bundle bundle = new Bundle();
				bundle.putInt("id", response.get(0).getId());
				bundle.putString("username", user_.getName());
				bundle.putInt("userid", user_.getId());

				Fragment fragment = new CustomGame();
				fragment.setArguments(bundle);

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
				mDrawerList.setItemChecked(0, false);
				mDrawerList.setItemChecked(1, false);
				mDrawerList.setItemChecked(2, false);
				mDrawerList.setItemChecked(3, false);
				setTitle("" + response.get(0).getId());
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		};
	};
}
