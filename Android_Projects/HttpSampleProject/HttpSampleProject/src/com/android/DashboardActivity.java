package com.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.R;
import com.android.entity.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		loadAndDisplayUsers();
	}

	private void loadAndDisplayUsers() {

		class HttpAsyncTask extends AsyncTask<String, Void, String> {

			private ProgressDialog progressDialog = new ProgressDialog(
					DashboardActivity.this);

			public HttpAsyncTask() {
				progressDialog.setTitle("Connecting....");
				progressDialog.setMessage("Connecting to the server");
				progressDialog.show();

			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			// This is the method to write the logic/code for the function that
			// has to be executed in the background without blocking the UI
			@Override
			protected String doInBackground(String... params) {

				try {

					// Create Http Client
					HttpClient httpClient = new DefaultHttpClient();

					HttpGet httpGet = new HttpGet(
							"http://www.myhomelibrary.net/test.json");

					// Execute the request. httpResponse catch the response from
					// the server
					// This is a blocking call, it waits till the response is
					// returned
					HttpResponse httpResponse = httpClient.execute(httpGet);

					// Read the response, we can use a normal java.io package
					InputStream inputStream = httpResponse.getEntity()
							.getContent();

					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(inputStream));

					StringBuilder responseStr = new StringBuilder();
					String responseLineStr = null;

					while ((responseLineStr = bufferedReader.readLine()) != null) {
						responseStr.append(responseLineStr);
					}
					return responseStr.toString();

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;

			}

			@Override
			protected void onPostExecute(String result) {

				super.onPostExecute(result);
				progressDialog.dismiss();

				if (result != null) {

					Toast.makeText(DashboardActivity.this,
							"Server response " + result, Toast.LENGTH_LONG)
							.show();

					try {
						// We know, the result/response has come
						JSONObject response = new JSONObject(result);
						
						JSONArray usersJsonArray = response.getJSONArray("user");
						String usernames[] = new String[usersJsonArray.length()];
						for (int i = 0; i < usersJsonArray.length(); i++) {
							
							User user = new User(usersJsonArray.getJSONObject(i));

							
							usernames[i] = user.getUsername();
							
						}
						
						ListView usersListView = (ListView) findViewById(R.id.users_list_view);
						
						ListAdapter adapter = new ArrayAdapter<String>(DashboardActivity.this,
								android.R.layout.simple_list_item_1, usernames);
						
						usersListView.setAdapter(adapter);
						
						

					} catch (Exception e) {

					}
				} else {

					// exception occured
				}
			}

		}

		HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
		httpAsyncTask.execute();

	}
}
