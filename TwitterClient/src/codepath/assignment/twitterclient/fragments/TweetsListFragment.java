package codepath.assignment.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.adapters.TweetsAdapter;
import codepath.assignment.twitterclient.models.Tweet;

public class TweetsListFragment extends Fragment {
	TweetsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetsAdapter(getActivity(), tweets);
		ListView lvTweets = (ListView) getActivity()
				.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
	}

	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
