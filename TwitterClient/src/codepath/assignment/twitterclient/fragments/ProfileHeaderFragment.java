package codepath.assignment.twitterclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.models.User;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileHeaderFragment extends Fragment {
	private TextView tvName;
	private TextView tvTagline;
	private TextView tvFollowers;
	private TextView tvFollowing;
	private ImageView ivProfile;
	private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile_header, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}

	private void setupViews() {
		tvName = (TextView) getActivity().findViewById(R.id.tvName);
		tvTagline = (TextView) getActivity().findViewById(R.id.tvTagline);
		tvFollowers = (TextView) getActivity().findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) getActivity().findViewById(R.id.tvFollowing);
		ivProfile = (ImageView) getActivity().findViewById(R.id.ivProfile);
		populateProfileHeader();
	}

	private void populateProfileHeader() {
		tvName.setText(user.getName());
		tvTagline.setText(user.getDescription());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
				ivProfile);
	}

	public void setUser(User user) {
		this.user = user;
	}

}
