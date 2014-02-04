package codepath.assignment.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.adapters.TweetAdapter;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.fragments.MentionsFragment;
import codepath.assignment.twitterclient.fragments.TimelineFragment;
import codepath.assignment.twitterclient.fragments.TweetFragment;
import codepath.assignment.twitterclient.models.Tweet;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener,
		TweetFragment.OnItemSelectedListener {

	private final int REQUEST_CODE_COMPOSE = 21;
	private final int REQUEST_CODE_PROFILE = 23;

	TweetFragment tweet_fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void onRefresh(MenuItem mi) {
		update();
	}

	public void update() {
		TwitterClientApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
						TweetAdapter adapter = new TweetAdapter(
								getBaseContext(), tweets);
						lvTweets.setAdapter(adapter);
					}
				});
	}

	public void onCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("action", "compose");
		startActivityForResult(i, REQUEST_CODE_COMPOSE);
	}

	public void onProfile(MenuItem miProfile) {
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		i.putExtra("action", "profile");
		startActivityForResult(i, REQUEST_CODE_PROFILE);
	}

	@Override
	public void onProfileSeleted(String userId) {
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		i.putExtra("user_id", userId);
		startActivityForResult(i, REQUEST_CODE_PROFILE);
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab home_tab = actionBar.newTab().setText("Home")
				.setTag("TimelineFragment").setTabListener(this);

		Tab mentions_tab = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment").setTabListener(this);

		actionBar.addTab(home_tab);
		actionBar.addTab(mentions_tab);
		actionBar.selectTab(home_tab);
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentManager fragment_manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = fragment_manager
				.beginTransaction();

		if (tab.getTag().equals("TimelineFragment"))
			fts.replace(R.id.flContainer, new TimelineFragment());
		else
			fts.replace(R.id.flContainer, new MentionsFragment());
		fts.commit();
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	}

}
