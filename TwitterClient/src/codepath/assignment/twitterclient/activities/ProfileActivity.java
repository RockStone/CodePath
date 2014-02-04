package codepath.assignment.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.fragments.ProfileFragment;
import codepath.assignment.twitterclient.fragments.TweetFragment;
import codepath.assignment.twitterclient.fragments.UserFragment;

public class ProfileActivity extends FragmentActivity implements
		TweetFragment.OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		String userId = getIntent().getStringExtra("userId");
		if ("".equals(userId)) {
			return;
		}

		FragmentTransaction fragment_transaction = getSupportFragmentManager()
				.beginTransaction();
		ProfileFragment userInfoFragment = ProfileFragment.newInstance(userId);
		UserFragment userTimelineFragment = UserFragment.newInstance(userId);
		fragment_transaction
				.replace(R.id.flUserContainer, userInfoFragment);
		fragment_transaction.replace(R.id.flTweetContainer,
				userTimelineFragment);
		fragment_transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public void onProfileSeleted(String userId) {
	}
}
