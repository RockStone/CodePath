package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.adapters.TweetAdapter;
import codepath.assignment.twitterclient.models.Tweet;

public class TweetFragment extends Fragment {

	private TweetAdapter tweet_adapter;
	private OnItemSelectedListener listener;

	public interface OnItemSelectedListener {
		public void onProfileSeleted(String userId);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
			this.listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString());
		}
	}

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

		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				Tweet tweet = (Tweet) adapter.getItemAtPosition(position);
				String userId = tweet.getUser().getUserIdStr();
				if ("".equals(userId)) {
					return;
				}
				listener.onProfileSeleted(userId);
			}
		});
	}

	public TweetAdapter getTweetsAdapter() {
		return tweet_adapter;
	}
}
