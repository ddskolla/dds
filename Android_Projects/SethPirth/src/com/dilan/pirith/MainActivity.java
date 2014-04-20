package com.dilan.pirith;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private Intent playerService;
	private ListView contactsListView;
	private ListAdapter adapter;
	private Button stopButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contactsListView = (ListView) findViewById(R.id.contacts_list_view);
		contactsListView.setOnItemClickListener(this);

		adapter = new ArrayAdapter<String>(this, R.layout.custom_textview,
				getDataForTheListView());
		contactsListView.setAdapter(adapter);

		stopButton = (Button) findViewById(R.id.button1);
		stopButton.setOnClickListener(this);
		stopButton.setVisibility(View.INVISIBLE);

		Toast.makeText(this, "Touch an item on the list to start playing",
				Toast.LENGTH_LONG).show();
	}

	private ArrayList<String> getDataForTheListView() {

		ArrayList<String> data = new ArrayList<String>();
		data.add("Seth_Piritha1");
		data.add("Seth_Piritha2");
		data.add("Seevali_Piritha1");
		data.add("Seevali_Piritha2");
		data.add("Antharaya_Niwarana_Piritha");
		return data;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Object o = arg0.getItemAtPosition(arg2);
		String keyword = o.toString();
		int resID = getResources().getIdentifier(
				"raw/" + keyword.toLowerCase(), "raw", getPackageName());

		playerService = new Intent(this, AudioService.class);
		playerService.putExtra("FLAG", "PLAY");
		playerService.putExtra("RESOURCE_ID", resID);
		startService(playerService);

		if (stopButton != null && stopButton.getVisibility() == View.INVISIBLE) {

			stopButton.setVisibility(View.VISIBLE);

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != playerService) {
			stopService(playerService);
		}
	}

	@Override
	public void onClick(View arg0) {

		if (arg0.getId() == R.id.button1) {

			playerService.putExtra("FLAG", "STOP");
			startService(playerService);

			if (stopButton.getVisibility() == View.VISIBLE) {
				stopButton.setVisibility(View.INVISIBLE);
				Toast.makeText(this,
						"Touch an item on the list to start playing",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.about) {
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
		}
		return super.onOptionsItemSelected(item);
	}

}
