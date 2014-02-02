package com.dilan.android;

import com.dilan.android.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MapRouteActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent navigation = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://maps.google.com/maps?saddr=-37.599910,145.095127&daddr=-37.820000,144.983300"));
		startActivity(navigation);

	}
}