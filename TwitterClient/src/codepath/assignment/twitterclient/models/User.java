package codepath.assignment.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User {
	@Column(name = "Name")
	public String name;
	
	@Column(name = "ScreenName")
	public String screenName;

	private long userId;
	private String userIdStr;
	private String profileImageUrl;
	private String profileBgImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;
	private String description;

	public String getName() {
		return name;
	}

	public long getId() {
		return userId;
	}

	public String getUserIdStr() {
		return userIdStr;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBgImageUrl;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public String getDescription() {
		return this.description;
	}

	public static User fromJson(JSONObject json) {
		User u = new User();
		try {
			u.name = json.getString("name");
			u.userId = json.getLong("id");
        	u.userIdStr = json.getString("id_str");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json
					.getString("profile_image_url");
			u.profileBgImageUrl = json
					.getString("profile_background_image_url");
			u.numTweets = json.getInt("statuses_count");
			u.followersCount = json.getInt("followers_count");
			u.friendsCount = json.getInt("friends_count");
			u.description = json.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}
}
