package com.hackathon.reportback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hackathon.reportback.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends Activity implements OnClickListener {
	TextView subject_field;
	TextView address_field;
	TextView emailbody_field;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		String TAG = "SettingActivity";
		Log.d(TAG, "came");
		Button savebtn = (Button) findViewById(R.id.settings_save_button);
		savebtn.setOnClickListener(this);
		subject_field = (TextView) findViewById(R.id.settings_subject);
		address_field = (TextView) findViewById(R.id.settings_email);
		emailbody_field = (TextView) findViewById(R.id.editText3);
		// being

		try {
			String Filename = "subjectfile";
			FileInputStream fis;
			String content = "";
			fis = openFileInput(Filename);
			byte[] input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			subject_field.setText(content);
			//
			Filename = "addressfile";

			content = "";
			fis = openFileInput(Filename);
			input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			address_field.setText(content);
			//
			Filename = "emailbodyfile";

			content = "";
			fis = openFileInput(Filename);
			input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			emailbody_field.setText(content);
			//
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
		}

		// end

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.settings_save_button:

			try {

				String FILENAME = "subjectfile";
				FileOutputStream fos = openFileOutput(FILENAME,
						Context.MODE_PRIVATE);
				if (subject_field.getText() != null
						&& subject_field.getText().length() > 0) {
					fos.write(subject_field.getText().toString().getBytes());
				} else {
					fos.write(" ".getBytes());
				}
				fos.close();

				FILENAME = "addressfile";
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				if (address_field.getText() != null
						&& address_field.getText().length() > 0) {
					fos.write(address_field.getText().toString().getBytes());
				} else {
					fos.write(" ".getBytes());
				}
				fos.close();

				FILENAME = "emailbodyfile";
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				if (emailbody_field.getText() != null
						&& emailbody_field.getText().length() > 0) {
					fos.write(emailbody_field.getText().toString().getBytes());
				} else {
					fos.write(" ".getBytes());
				}
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();

			break;
		}
		// TODO Auto-generated method stub

	}

}
