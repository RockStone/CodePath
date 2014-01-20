package com.codepath.assignment.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearch extends Activity {

	private final int REQUEST_CODE = 21;

	EditText etSearchContent;
	GridView gvImageResults;
	Button btSearch;

	String image_setting = null;

	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	AsyncHttpClient client = new AsyncHttpClient();

	ImageResultArrayAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvImageResults.setAdapter(imageAdapter);
		gvImageResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				Intent i = new Intent(getApplicationContext(),
						ImageDisplay.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
		gvImageResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadMore(page);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

	public void setupViews() {
		etSearchContent = (EditText) findViewById(R.id.etSearchContent);
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
		btSearch = (Button) findViewById(R.id.btSearch);
	}

	public void onImageSearch(View v) {
		String query = etSearchContent.getText().toString();

		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
				.show();
		client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
						+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageResults.clear();
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResults));
							Log.d("debug", imageResults.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public void loadMore(int offset) {
		String query = etSearchContent.getText().toString();

		client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
						+ "start=" + offset + "&v=1.0&q=" + Uri.encode(query)
						+ "&imgsz=" + image_setting,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResults));
							Log.d("debug", imageResults.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public void onFilter(MenuItem mi) {
		// Toast.makeText(this, "Image Filter", Toast.LENGTH_SHORT).show();
		Intent filter = new Intent(this, ImageFilter.class);
		filter.putExtra("note", "Image Filter");
		startActivityForResult(filter, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent settings) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			image_setting = settings.getExtras().getString("image_setting");
			String query = etSearchContent.getText().toString();

			client.get(
					"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
							+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query)
							+ image_setting, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject response) {
							JSONArray imageJsonResults = null;
							try {
								imageJsonResults = response.getJSONObject(
										"responseData").getJSONArray("results");
								imageResults.clear();
								imageAdapter.addAll(ImageResult
										.fromJSONArray(imageJsonResults));
								Log.d("debug", imageResults.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		}
	}
}
