package codepath.assignment.twitterclient.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.clients.TwitterClientApp;
import codepath.assignment.twitterclient.models.User;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileFragment extends Fragment {
	private String userId;
	private User user;

	TextView tvUserName;
	TextView tvUserDescription;
	TextView tvFollower;
	TextView tvFollowing;
	ImageView ivUserProfile;

	public static ProfileFragment newInstance(String userId) {
		ProfileFragment userInfo = new ProfileFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		userInfo.setArguments(args);
		return userInfo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = getArguments().getString("userId", "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentActivity activity = getActivity();

		tvUserName = (TextView) activity.findViewById(R.id.tvUserName);
		tvUserDescription = (TextView) activity.findViewById(R.id.tvUserDescription);
		tvFollower = (TextView) activity.findViewById(R.id.tvFollower);
		tvFollowing = (TextView) activity.findViewById(R.id.tvFollowing);
		ivUserProfile = (ImageView) activity
				.findViewById(R.id.ivUserProfile);

		TwitterClientApp.getRestClient().getUserShow(userId,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonUser) {
						user = User.fromJson(jsonUser);
						ImageLoader.getInstance().displayImage(
								user.getProfileImageUrl(), ivUserProfile);
						tvUserName.setText(user.getName());
						tvUserDescription.setText(user.getDescription());
						tvFollower.setText(user.getFollowersCount());
						tvFollowing.setText(user.getFriendsCount());
					}
				});

	}

}
