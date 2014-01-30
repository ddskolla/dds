package com.hackathon.reportback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
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

public class CoreActivity extends Activity implements OnClickListener {

	private int mOrientation = -1;
	private static final String tag = "orient";

	private static final int ORIENTATION_PORTRAIT_NORMAL = 1;
	private static final int ORIENTATION_PORTRAIT_INVERTED = 2;
	private static final int ORIENTATION_LANDSCAPE_NORMAL = 3;
	private static final int ORIENTATION_LANDSCAPE_INVERTED = 4;

	CameraUtil cameraUtil;
	ImageButton buttontakePicture;
	ImageButton buttonRecordAudio;
	AudioRecorderUtil audioRecorder;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		logMessageinDebug("In onCreate");

		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);

		// create new instance to the surface
		cameraUtil = new CameraUtil(this);

		// set the surface to the xml in the surface
		//((FrameLayout) findViewById(R.id.preview)).addView(cameraUtil);
		// set button listener.
		//buttontakePicture = (ImageButton) findViewById(R.id.button_take_picture);
		//buttonRecordAudio = (ImageButton) findViewById(R.id.button_record_audio);
		buttontakePicture.setOnClickListener(this);
		buttonRecordAudio.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		logMessageinDebug("In onCreateOptionsMenu");

	//	getMenuInflater().inflate(R.menu.activity_core, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		switch (item.getItemId()) {

		case R.id.menu_settings:
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d(tag, "starting acttivity");
            startActivity(intent);
			toastMessage("Settings");

			break;

		}
*/
		return super.onOptionsItemSelected(item);

	}

	
	public void onClick(View v) {

	//	if (v.getId() == R.id.button_take_picture) {

			cameraUtil.camera.takePicture(shutterCallback, rawCallback,
					jpegCallback);
	//	} else if (v.getId() == R.id.button_record_audio) {

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

					setCameraFocus(myAutoFocusCallback);

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
}
