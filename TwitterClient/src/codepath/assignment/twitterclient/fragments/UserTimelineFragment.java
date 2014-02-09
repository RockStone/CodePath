package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.models.Tweet;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	public static UserTimelineFragment newInstance(String userId) {
		UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String userId = getArguments().getString("userId", ""); 
		
		TwitterClientApp.getRestClient().getUserTimeLine(userId, 
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						getAdapter().addAll(tweets);
					}
				});
	}
}
