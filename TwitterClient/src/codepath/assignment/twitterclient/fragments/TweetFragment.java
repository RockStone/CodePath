package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.adapters.TweetAdapter;
import codepath.assignment.twitterclient.models.Tweet;

public class TweetFragment extends Fragment {

	private TweetAdapter tweet_adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tweet, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ListView lvTweets = (ListView) getActivity()
				.findViewById(R.id.lvTweets);
		tweet_adapter = new TweetAdapter(getActivity(), tweets);
		lvTweets.setAdapter(tweet_adapter);
	}

	public TweetAdapter getTweetAdapter() {
		return tweet_adapter;
	}
}
