package com.codepath.assignment.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

//import android.widget.Toast;

public class ImageFilter extends Activity {

	String[] image_size, image_color, image_type;
	int image_size_index, image_color_index, image_type_index;
	EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_filter);
		setupViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_filter, menu);
		return true;
	}

	public void setupViews() {
		Spinner spImageSize = (Spinner) findViewById(R.id.spImageSize);

		ArrayAdapter<CharSequence> apImageSize = ArrayAdapter
				.createFromResource(this, R.array.image_size_array,
						android.R.layout.simple_spinner_item);

		apImageSize
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageSize.setAdapter(apImageSize);
		spImageSize.setOnItemSelectedListener(selectedListener);

		Spinner spImageColor = (Spinner) findViewById(R.id.spImageColor);

		ArrayAdapter<CharSequence> apImageColor = ArrayAdapter
				.createFromResource(this, R.array.image_color_array,
						android.R.layout.simple_spinner_item);

		apImageColor
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageColor.setAdapter(apImageColor);
		spImageColor.setOnItemSelectedListener(selectedListener);

		Spinner spImageType = (Spinner) findViewById(R.id.spImageType);

		ArrayAdapter<CharSequence> apImageType = ArrayAdapter
				.createFromResource(this, R.array.image_type_array,
						android.R.layout.simple_spinner_item);

		apImageType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageType.setAdapter(apImageType);
		spImageType.setOnItemSelectedListener(selectedListener);

		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}

	private OnItemSelectedListener selectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			switch (parent.getId()) {
			case R.id.spImageSize:
				image_size = getResources().getStringArray(
						R.array.image_size_array);
				image_size_index = parent.getSelectedItemPosition();
				break;
			case R.id.spImageColor:
				image_color = getResources().getStringArray(
						R.array.image_color_array);
				image_color_index = parent.getSelectedItemPosition();
				break;
			case R.id.spImageType:
				image_type = getResources().getStringArray(
						R.array.image_type_array);
				image_type_index = parent.getSelectedItemPosition();
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// do nothing
		}
	};

	public void onDone(MenuItem mi) {
		String image_setting = "&imgsz=" + image_size[image_size_index]
				+ "&imgcolor=" + image_color[image_color_index] + "&imgtype="
				+ image_type[image_type_index] + "&as_sitesearch="
				+ etSiteFilter.getText().toString();
		Intent settings = new Intent();
		settings.putExtra("image_setting", image_setting);
		setResult(RESULT_OK, settings);
		finish();
	}
}
