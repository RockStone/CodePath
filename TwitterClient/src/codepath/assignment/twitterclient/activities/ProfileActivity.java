package codepath.assignment.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.fragments.ProfileHeaderFragment;
import codepath.assignment.twitterclient.fragments.UserTimelineFragment;
import codepath.assignment.twitterclient.models.User;

public class ProfileActivity extends FragmentActivity {
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		this.user = (User) getIntent().getSerializableExtra("user");
		String userId = Long.toString(user.getId());
		loadUserProfile(userId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public void loadUserProfile(String userId) {
		getActionBar().setTitle("@" + user.getScreenName());
		FragmentTransaction fts = getSupportFragmentManager()
				.beginTransaction();
		ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
		UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(userId);
		profileHeaderFragment.setUser(user);
		fts.replace(R.id.frame_profile_header_container, profileHeaderFragment);
		fts.replace(R.id.frame_user_timeline_container, userTimelineFragment);
		fts.commit();
	}
}
