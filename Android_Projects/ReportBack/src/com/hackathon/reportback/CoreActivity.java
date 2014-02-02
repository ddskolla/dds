package com.hackathon.reportback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hackathon.reportback.util.AudioRecorderUtil;
import com.hackathon.reportback.util.CameraUtil;
import com.hackathon.reportback.util.FolderArchiveUtil;
import com.hackathon.reportback.util.ZipUtility;

public class CoreActivity extends Activity implements OnClickListener {

	private int mOrientation = -1;

	private static final int ORIENTATION_PORTRAIT_NORMAL = 1;
	private static final int ORIENTATION_PORTRAIT_INVERTED = 2;
	private static final int ORIENTATION_LANDSCAPE_NORMAL = 3;
	private static final int ORIENTATION_LANDSCAPE_INVERTED = 4;

	CameraUtil cameraUtil;
	ImageButton buttontakePicture;
	ImageButton buttonRecordAudio;
	ImageButton buttonSendEmail;
	AudioRecorderUtil audioRecorder;
	ArrayList<Uri> uriListToEmail;

	String emailSubject;
	String emailText;
	String emailAddress;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		logMessageinDebug("In onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// paste begin

		// paste end

		// create new instance to the surface
		cameraUtil = new CameraUtil(this);

		// set the surface to the xml in the surface
		((FrameLayout) findViewById(R.id.preview)).addView(cameraUtil);

		// set button listeners.
		buttontakePicture = (ImageButton) findViewById(R.id.button_take_picture);
		buttonRecordAudio = (ImageButton) findViewById(R.id.button_record_audio);
		buttonSendEmail = (ImageButton) findViewById(R.id.button_send_mail);
		buttontakePicture.setOnClickListener(this);
		buttonRecordAudio.setOnClickListener(this);
		buttonSendEmail.setOnClickListener(this);

		if (!checkFileExistance()) {
			Intent intent = new Intent(this, SettingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			toastMessage("Settings");
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		logMessageinDebug("In onCreateOptionsMenu");

		getMenuInflater().inflate(R.menu.activity_core, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			toastMessage("Settings");
			startActivity(intent);
			break;

		case R.id.about:

			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);

		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button_take_picture) {

			setCameraFocus(myAutoFocusCallback);
			cameraUtil.camera.takePicture(shutterCallback, rawCallback,
					jpegCallback);

		} else if (v.getId() == R.id.button_send_mail) {

			if (buttonRecordAudio.isSelected() && audioRecorder != null) {

				try {
					buttonRecordAudio.setSelected(false);
					audioRecorder.stop();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			loadSettings();

			toastMessage("Send Pressed");

			Toast.makeText(getApplicationContext(), "Zipping Start",
					Toast.LENGTH_SHORT).show();

			File imageDirectory = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/ReportBack/Images");

			File audioDirectory = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/ReportBack/Audio");

			File imageZipDirectory = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/Reportback/Images.zip");

			File audioZipDirectory = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/Reportback/Audio.zip");

			try {
				uriListToEmail = new ArrayList<Uri>();

				if (imageDirectory.exists()) {
					ZipUtility.zipDirectory(imageDirectory, imageZipDirectory);
					uriListToEmail.add(Uri.fromFile(imageZipDirectory));
					File imageDir = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/ReportBack/Images");
					File destinationImageDir = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/ReportBack/Archive/Images");
					FolderArchiveUtil.copyDirectory(imageDir,
							destinationImageDir);
					FolderArchiveUtil.delete(imageDir);
					Toast.makeText(getApplicationContext(), "Images Done",
							Toast.LENGTH_SHORT).show();
				}

				if (audioDirectory.exists()) {
					ZipUtility.zipDirectory(audioDirectory, audioZipDirectory);
					uriListToEmail.add(Uri.fromFile(audioZipDirectory));
					File audioDir = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/ReportBack/Audio");
					File destinationAudioDir = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/ReportBack/Archive/Audio");
					FolderArchiveUtil.copyDirectory(audioDir,
							destinationAudioDir);
					FolderArchiveUtil.delete(audioDir);
					Toast.makeText(getApplicationContext(), "Audio Done",
							Toast.LENGTH_SHORT).show();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (null != uriListToEmail && !uriListToEmail.isEmpty()) {

				String emailSubject = this.emailSubject;
				String emailText = this.emailText;
				String emailAddress = this.emailAddress;

				try {

					// String emailAddress =
					// "chamarais@gmail.com,bogzyrox@gmail.com,chamarais@gmail.com,rajithad@gmail.com";
					// String emailSubject =
					// "Capture The Moment : See Attachments";
					// String emailText =
					// "Pleasefind attached, the Audio and images captured By : \"Capture the Moment\"";

					String[] mailAddresses = { emailAddress };

					Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
					// sendIntent.setType("application/zip");
					sendIntent.setType("message/rfc822");
					sendIntent.putExtra(Intent.EXTRA_EMAIL, mailAddresses);
					sendIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
					sendIntent.putExtra(Intent.EXTRA_STREAM, uriListToEmail);
					sendIntent.putExtra(Intent.EXTRA_TEXT, emailText);

					startActivity(Intent.createChooser(sendIntent, "Send mail"));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				toastMessage("You need to take a picture or record an audio in order to send an e-mail !");
			}

		} else if (v.getId() == R.id.button_record_audio) {

			try {
				if (buttonRecordAudio.isSelected()) {
					toastMessage("Recording Stopped");
					buttonRecordAudio.setSelected(false);
					audioRecorder.stop();
					logMessageinDebug("Recording Stopped");
				} else {
					toastMessage("Recording Audio");
					buttonRecordAudio.setSelected(true);

					audioRecorder = new AudioRecorderUtil("/ReportBack/Audio"
							+ "/" + System.currentTimeMillis() + ".3gp");

					audioRecorder.start();

					logMessageinDebug("Recording Started");

				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	// Happens when picture is taken
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};

	// Happens when the image fills the buffer
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};

	// happen when the jepg buffer fill.
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				//
				// cut all
				// crate the metadata of the image
				ContentValues image = new ContentValues();

				long imagName = System.currentTimeMillis();

				// add metadata tags:
				// can add geo tagging if required.
				image.put(Media.DISPLAY_NAME, imagName);
				image.put(Media.MIME_TYPE, "image/jpg");
				image.put(Media.TITLE, "Reportback image : " + imagName);
				image.put(Media.DESCRIPTION,
						"Image taken from the Reportback activity");

				// write the rotation information of the image
				switch (mOrientation) {
				case ORIENTATION_PORTRAIT_NORMAL:
					image.put(Media.ORIENTATION, 90);
					break;
				case ORIENTATION_LANDSCAPE_NORMAL:
					image.put(Media.ORIENTATION, 0);
					break;
				case ORIENTATION_PORTRAIT_INVERTED:
					image.put(Media.ORIENTATION, 270);
					break;
				case ORIENTATION_LANDSCAPE_INVERTED:
					image.put(Media.ORIENTATION, 180);
					break;
				}

				// set the stream for the image
				OutputStream imageFileOS;

				try {

					File folder = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/ReportBack/Images");
					folder.mkdirs();

					String imageUrl = String.format(folder.toString()
							+ "/%d.jpg", System.currentTimeMillis());
					imageFileOS = new FileOutputStream(imageUrl);

					imageFileOS.write(data);

					// close the stream
					imageFileOS.flush();
					imageFileOS.close();

					toastMessage("imageSaved");

					cameraUtil.camera.startPreview();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	};

	public void setCameraFocus(AutoFocusCallback autoFocus) {
		if (cameraUtil.camera.getParameters().getFocusMode()
				.equals(Camera.Parameters.FOCUS_MODE_AUTO)
				|| cameraUtil.camera.getParameters().getFocusMode()
						.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {

			cameraUtil.camera.autoFocus(autoFocus);
		}
	}

	private AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback() {

		public void onAutoFocus(boolean autoFocusSuccess, Camera arg1) {

		}
	};

	/**
	 * Log the given message in Debug mode.
	 * 
	 * @param message
	 *            to be logged.
	 */
	private void logMessageinDebug(String message) {

		String TAG = "CoreActivity";
		Log.d(TAG, "message");

	}

	private void toastMessage(String message) {

		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	private boolean checkFileExistance() {

		File addressFile = this.getFileStreamPath("addressfile");
		File emailBodyFile = this.getFileStreamPath("emailbodyfile");
		File subjectFile = this.getFileStreamPath("subjectfile");
		if (!addressFile.exists() || !emailBodyFile.exists()
				|| !subjectFile.exists()) {
			return false;

		} else {
			return true;
		}

	}

	private void loadSettings() {

		try {
			String Filename = "subjectfile";
			FileInputStream fis;
			String content = "";
			fis = openFileInput(Filename);
			byte[] input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			emailSubject = content;
			//
			Filename = "addressfile";

			content = "";
			fis = openFileInput(Filename);
			input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			emailAddress = content;
			//
			Filename = "emailbodyfile";

			content = "";
			fis = openFileInput(Filename);
			input = new byte[fis.available()];
			while (fis.read(input) != -1) {
			}
			content += new String(input);
			emailText = content;
			//
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
		}

	}

}
