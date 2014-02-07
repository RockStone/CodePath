package codepath.assignment.twitterclient.activities;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.fragments.HomeTimelineFragment;
import codepath.assignment.twitterclient.fragments.MentionsFragment;
import codepath.assignment.twitterclient.models.User;

import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeActivity extends FragmentActivity implements TabListener {
	private final int REQUEST_CODE = 21;
	private ActionBar actionBar;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		setupUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	private void setupNavigationTabs() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsTimelineFragment")
				.setIcon(R.drawable.ic_mention).setTabListener(this);

		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);

	}

	public void setupUser() {
		TwitterClientApp.getRestClient().getUserInfo(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonUser) {
						user = User.fromJson(jsonUser);
						setTitle("@" + user.getScreenName());
					}
				});
	}

	public void onCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("action", "compose");
		startActivityForResult(i, REQUEST_CODE);
	}

	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", user);
		startActivity(i);
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();

		android.support.v4.app.FragmentTransaction fts = manager
				.beginTransaction();

		if (tab.getTag() == "HomeTimelineFragment") {
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frame_container, new MentionsFragment());
		}

		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
