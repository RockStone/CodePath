package codepath.assignment.twitterclient.activities;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.models.User;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	private void loadProfile() {
		TwitterClientApp.getRestClient().getUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				User user = User.fromJson(jsonUser);
				getActionBar().setTitle("@" + user.getScreenName());
				showProfile(user);
			}

			private void showProfile(User user) {
				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
				TextView tvFollower = (TextView) findViewById(R.id.tvFollower);
				TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

				tvUserName.setText(user.getName());
				tvFollower.setText(user.getFollowersCount() + " Followers");
				tvFollowing.setText(user.getFriendsCount() + " Following");

				ImageLoader.getInstance().displayImage(
						user.getProfileImageUrl(), ivProfileImage);
			}
		});
	}
}
