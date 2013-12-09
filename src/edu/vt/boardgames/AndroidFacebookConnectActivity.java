package edu.vt.boardgames;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;






//import com.facebook.boardgames.R;
import edu.vt.boardgames.R;
import edu.vt.boardgames.network.Game;
import edu.vt.boardgames.network.User;
import edu.vt.boardgames.network.UtilsServer;
import edu.vt.boardgames.network.response.HandlerResponse;

public class AndroidFacebookConnectActivity extends Fragment {

	// Your Facebook APP ID
	private static String APP_ID = "716260365052343"; 

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	ProgressDialog progress;

	// Buttons
	Button btnFbLogin;
	Button btnFbGetProfile;
	Button btnPostToWall;
	Button btnShowAccessTokens;
	EditText nameFacebook, NameText, EmailFacebook, EmailText, UserText, UserFacebook;

	//@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		View rootView = inflater.inflate(R.layout.main_f, container, false);
		btnFbLogin = (Button)rootView.findViewById(R.id.btn_fblogin);
		btnFbGetProfile = (Button)rootView. findViewById(R.id.btn_get_profile);
		btnPostToWall = (Button) rootView.findViewById(R.id.btn_fb_post_to_wall);
		nameFacebook =(EditText) rootView.findViewById(R.id.text_name_profile);
		NameText =(EditText) rootView.findViewById(R.id.Name_Text);
		EmailFacebook =(EditText) rootView.findViewById(R.id.text_email_profile);
		EmailText =(EditText) rootView.findViewById(R.id.Name_Email);
		UserText =(EditText) rootView.findViewById(R.id.Name_User);
		UserFacebook =(EditText) rootView.findViewById(R.id.text_user_profile);


//		btnShowAccessTokens = (Button) rootView.findViewById(R.id.btn_show_access_tokens);

//		btnFbLogin = (Button) findViewById(R.id.btn_fblogin);
//		btnFbGetProfile = (Button) findViewById(R.id.btn_get_profile);
//		btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
//		btnShowAccessTokens = (Button) findViewById(R.id.btn_show_access_tokens);
		mAsyncRunner = new AsyncFacebookRunner(facebook);

		/**
		 * Login button Click event
		 * */
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				progress = ProgressDialog.show(getActivity(), "Wait!", "Signing in", true, false);
				loginToFacebook();
			}
		});



		/**
		 * Posting to Facebook Wall
		 * */
		btnPostToWall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				postToWall();
			}
		});

		/**
		 * Showing Access Tokens
		 * */
//		btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showAccessTokens();
//			}
//		});
		return rootView;
	}

	/**
	 * Function to login into facebook
	 * */
	public void loginToFacebook() {
		mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
				//(Context.MODE_PRIVATE);
		//mPrefs = this.getActivity().getPreferences(MODE_PRIVATE);
		//mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			
			btnFbLogin.setVisibility(View.INVISIBLE);
		

			// Making post to wall visible
			btnPostToWall.setVisibility(View.VISIBLE);

			mAsyncRunner.request("me", new RequestListener() {
				@Override
				public void onComplete(String response, Object state) {
					Log.d("Profile", response);
					String json = response;
					try {
						// Facebook Profile JSON data
						JSONObject profile = new JSONObject(json);
						
						// getting name of the user
						final String name = profile.getString("name");
						
						// getting email of the user
						final String email = profile.getString("email");
						final String user = profile.getString("username");
						


						
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								//btnFbGetProfile.setVisibility(View.INVISIBLE);
								nameFacebook.setText(name);
								UserFacebook.setText(user);
								EmailFacebook.setText(email);
								
								
								EmailText.setVisibility(View.VISIBLE);
								EmailFacebook.setVisibility(View.VISIBLE);
								UserText.setVisibility(View.VISIBLE);

								NameText.setVisibility(View.VISIBLE);
								nameFacebook.setVisibility(View.VISIBLE);
								UtilsServer.createOrLoginUser(handler, user);
								//Toast.makeText(getActivity().getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
							}

						});

						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onIOException(IOException e, Object state) {
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e,
						Object state) {
				}

				@Override
				public void onMalformedURLException(MalformedURLException e,
						Object state) {
				}

				@Override
				public void onFacebookError(FacebookError e, Object state) {
				}
			});
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}
		
		
		if (!facebook.isSessionValid()) {
			facebook.authorize(this.getActivity(),
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

							// Making Login button invisible
							btnFbLogin.setVisibility(View.INVISIBLE);

							// Making logout Button visible
							//btnFbGetProfile.setVisibility(View.VISIBLE);

							// Making post to wall visible
							btnPostToWall.setVisibility(View.VISIBLE);

							mAsyncRunner.request("me", new RequestListener() {
								@Override
								public void onComplete(String response, Object state) {
									Log.d("Profile", response);
									String json = response;
									try {
										// Facebook Profile JSON data
										JSONObject profile = new JSONObject(json);
										
										// getting name of the user
										final String name = profile.getString("name");
										
										// getting email of the user
										final String email = profile.getString("email");
										final String user = profile.getString("username");
										


										
										getActivity().runOnUiThread(new Runnable() {

											@Override
											public void run() {
												//btnFbGetProfile.setVisibility(View.INVISIBLE);
												nameFacebook.setText(name);
												UserFacebook.setText(user);
												EmailFacebook.setText(email);
												
												
												EmailText.setVisibility(View.VISIBLE);
												EmailFacebook.setVisibility(View.VISIBLE);
												UserText.setVisibility(View.VISIBLE);

												NameText.setVisibility(View.VISIBLE);
												nameFacebook.setVisibility(View.VISIBLE);
												UtilsServer.createOrLoginUser(handler, user);
												//progress = ProgressDialog.show(getActivity(), "Wait!", "Signing in", true, false);
												//Toast.makeText(getActivity().getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
											}

										});

										
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onIOException(IOException e, Object state) {
								}

								@Override
								public void onFileNotFoundException(FileNotFoundException e,
										Object state) {
								}

								@Override
								public void onMalformedURLException(MalformedURLException e,
										Object state) {
								}

								@Override
								public void onFacebookError(FacebookError e, Object state) {
								}
							});
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}




	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		facebook.dialog(this.getActivity(), "feed", new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}
	private HandlerResponse<User> handler = new HandlerResponse<User>()
	{
		public void onResponseArrayObj(java.util.ArrayList<User> response) {
			
			progress.dismiss();
			
			if (response != null && response.size() > 0)
			{
				MainActivity.user_ = response.get(0);
			}
		};
	};
		


}