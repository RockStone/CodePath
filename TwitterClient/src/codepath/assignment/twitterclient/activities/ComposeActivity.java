package codepath.assignment.twitterclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import codepath.assignment.twitterclient.R;
import codepath.assignment.twitterclient.clients.TwitterClientApp;

public class ComposeActivity extends Activity {
	private EditText etTweet;
	private TextView tvTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void setupViews() {
		etTweet = (EditText) findViewById(R.id.etTweet);
		tvTweet = (TextView) findViewById(R.id.tvTweet);

		final TextWatcher twTweet = new TextWatcher() {
			private final int maxCharacters = 140;
			private int beforeCursorPosition = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeCursorPosition = start;
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				tvTweet.setText(String.valueOf(s.length()));
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() >= maxCharacters) {
					etTweet.setSelection(beforeCursorPosition);
					Toast.makeText(getBaseContext(),
							R.string.gossip_leak,
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		etTweet.requestFocus();
		etTweet.addTextChangedListener(twTweet);
	}

	public void postTweet(View v) {
		String tweet = etTweet.getText().toString();
		if ("".equals(tweet)) {
			Toast.makeText(getBaseContext(), R.string.say_something,
					Toast.LENGTH_SHORT).show();
			return;
		}

		TwitterClientApp.getRestClient().postTweet(tweet, null);

		Intent i = new Intent();
		i.putExtra("action", "tweet");
		setResult(RESULT_OK, i);
		finish();
	}

	public void onCancel(View v) {
		Intent i = new Intent();
		i.putExtra("action", "cancel");
		setResult(RESULT_OK, i);
		finish();
	}
}
