package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.models.Tweet;

import com.loopj.android.http.JsonHttpResponseHandler;


public class MentionsFragment extends TweetFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getMentionsTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						getTweetAdapter().addAll(tweets);
					}

					@Override
					public void onFailure(java.lang.Throwable e,
							JSONObject errorResponse) {
						ArrayList<Tweet> tweets = new ArrayList<Tweet>();
						getTweetAdapter().addAll(tweets);
					}
				});
	}
}
