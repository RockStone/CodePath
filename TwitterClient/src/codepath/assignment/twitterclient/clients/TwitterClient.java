package codepath.assignment.twitterclient.clients;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "j27y73tQv4MeKdnUkAMzMw";
	public static final String REST_CONSUMER_SECRET = "fS819tiPZZmD876gWHUin9h9I1SBhtjTlW5IHgwAo";
	public static final String REST_CALLBACK_URL = "http://rockstone.me";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        client.get(apiUrl, params, handler);
	}

	public void getUser(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
	
	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		client.post(apiUrl, params, handler);
	}
}
