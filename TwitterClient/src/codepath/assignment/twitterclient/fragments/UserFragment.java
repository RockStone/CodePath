package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.models.Tweet;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserFragment extends TweetFragment {
	private String userId;

	public static UserFragment newInstance(String userId) {
		UserFragment userTimeline = new UserFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		userTimeline.setArguments(args);
		return userTimeline;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = getArguments().getString("userId", "");

		TwitterClientApp.getRestClient().getUserTimeline(userId,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						getTweetsAdapter().addAll(tweets);
					}
				});
	}

}