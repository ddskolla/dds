package com.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HttpActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button signInPostButton = (Button) findViewById(R.id.sign_in_post_button);
		Button signInGetButton = (Button) findViewById(R.id.sign_in_get_button);

		signInPostButton.setOnClickListener(this);
		signInGetButton.setOnClickListener(this);

	}

	private void signInWithPost(String username, String password) {

		// This is the method to write the asynchronous call to the server
		// normally done with java threads, but android gives a better option
		class SignInWithPostAsyncTask extends AsyncTask<String, Void, String> {

			private ProgressDialog progressDialog = new ProgressDialog(
					HttpActivity.this);

			public SignInWithPostAsyncTask() {
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

					HttpPost httpPost = new HttpPost(
							"http://www.mmedipro.sh7.us/learnandroidnow/checklogging.php");

					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
							2);
					nameValuePair.add(new BasicNameValuePair("username",
							params[0]));
					nameValuePair.add(new BasicNameValuePair("password",
							params[1]));

					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

					// Execute the request. httpResponse catch the response from
					// the server
					// This is a blocking call, it waits till the response is
					// returned
					HttpResponse httpResponse = httpClient.execute(httpPost);

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
					if (result.equals("success")) {
						
						Intent intent = new Intent(HttpActivity.this, DashboardActivity.class);
						startActivity(intent);
						
					}
				}
			}

		}

		SignInWithPostAsyncTask signInWithPostAsyncTask = new SignInWithPostAsyncTask();
		signInWithPostAsyncTask.execute(username, password);

	}

	private void signInWithGet(String username, String password) {

		// This is the method to write the asynchronous call to the server
		// normally done with java threads, but android gives a better option
		class SignInWithGetAsyncTask extends AsyncTask<String, Void, String> {

			private ProgressDialog progressDialog = new ProgressDialog(
					HttpActivity.this);

			public SignInWithGetAsyncTask() {
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
							"http://www.mmedipro.sh7.us/learnandroidnow/checklogging_get.php?username="
									+ params[0] + "&password=" + params[1]);

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

					Toast.makeText(HttpActivity.this,
							"Server response " + result, Toast.LENGTH_LONG)
							.show();
					// We know, the result/response has come

				} else {

					// exception occured
				}
			}

		}

		SignInWithGetAsyncTask signInWithGetAsyncTask = new SignInWithGetAsyncTask();
		signInWithGetAsyncTask.execute(username, password);

	}

	@Override
	public void onClick(View view) {

		EditText usernameEditText = (EditText) findViewById(R.id.username_edit_text);
		EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		if (view.getId() == R.id.sign_in_post_button) {

			signInWithPost(username, password);

		} else {

			signInWithGet(username, password);
		}
	}

}