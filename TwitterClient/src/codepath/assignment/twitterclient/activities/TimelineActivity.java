package codepath.assignment.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.adapters.TweetsAdapter;
import codepath.assignment.twitterclient.models.Tweet;
import codepath.assignment.twitterclient.models.User;
import codepath.assignment.twitterclient.singletons.TwitterClientApp;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private final int REQUEST_CODE = 21;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void setupViews() {
		TwitterClientApp.getRestClient().getUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				user = User.fromJson(jsonUser);
				setTitle("@" + user.getScreenName());
			}
		});

		update();
	}

	public void onCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("action", "compose");
		startActivityForResult(i, REQUEST_CODE);
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
						TweetsAdapter adapter = new TweetsAdapter(
								getBaseContext(), tweets);
						lvTweets.setAdapter(adapter);
					}
				});
	}
}
