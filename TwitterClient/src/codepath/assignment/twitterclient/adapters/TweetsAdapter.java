package codepath.assignment.twitterclient.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.activities.ProfileActivity;
import codepath.assignment.twitterclient.models.Tweet;
import codepath.assignment.twitterclient.models.User;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, ArrayList<Tweet> tweets) {
		super(context, 0, tweets);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}

		Tweet tweet = getItem(position);

		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), imageView);

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>"
				+ " <small><font color='#777777'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));

		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));

		imageView.setTag(tweet.getUser());
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				User user = (User) v.getTag();
				Context ctxt = getContext();
				Intent i = new Intent(ctxt, ProfileActivity.class);
				i.putExtra("user", user);
				ctxt.startActivity(i);
			}
		});

		return view;
	}
}
