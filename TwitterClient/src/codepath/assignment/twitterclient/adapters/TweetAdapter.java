package codepath.assignment.twitterclient.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.models.Tweet;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class TweetAdapter extends ArrayAdapter<Tweet> {
	public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
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
		imageLoader.displayImage(
				tweet.getUser().getProfileBackgroundImageUrl(), imageView);

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>"
				+ " <small><font color='#777777'>@"
				+ tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));

		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}
}
