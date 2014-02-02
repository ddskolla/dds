package com.hackathon.reportback.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraUtil extends SurfaceView implements SurfaceHolder.Callback {

	// Interface to the display the surface
	SurfaceHolder mHolder;
	// The cam instance
	public Camera camera;

	public CameraUtil(Context context) {
		super(context);

		logMessageinDebug("Constructor called in class CameraUtil");
		// get the holder of the surface
		mHolder = getHolder();
		// create callback interface to the holder
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// creating the surface and connecting to the camera
	public void surfaceCreated(SurfaceHolder holder) {

		logMessageinDebug("In surfaceCreated");
		camera = Camera.open();
		// Need to work on this to fix portrait / landscape flip image skewing
		// issue.

		int orientation = getResources().getConfiguration().orientation;
		// Determine Portrait or Landscape and adjust the camera orientation
		// accordingly.
		
		if (orientation == 1) {
			camera.setDisplayOrientation(90);
		} else {
			camera.setDisplayOrientation(0);
		}

		try {

			// set the camera display to the holder
			camera.setPreviewDisplay(holder);
			camera.startPreview();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// called when the surface is destroyed.
	public void surfaceDestroyed(SurfaceHolder holder) {

		logMessageinDebug("In surfaceDestroyed");

		if (camera != null) {
			camera.stopPreview();
			// Releasing the resource.
			camera.release();
		}
	}

	public Camera.Parameters getParam() {

		logMessageinDebug("In getParam");

		Camera.Parameters parameters = camera.getParameters();

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			parameters.set("orientation", "portrait");
			parameters.set("rotation", 90);
		}
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			parameters.set("orientation", "landscape");
			parameters.set("rotation", 90);
		}

		return parameters;

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		logMessageinDebug("In surfaceChanged");

		// Connecting to the camera again upon surface change
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(w, h);
		camera.setParameters(parameters);
		try {
			camera.setPreviewDisplay(mHolder);
			camera.startPreview();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Log the given message in Debug mode.
	 * 
	 * @param message
	 *            to be logged.
	 */
	private void logMessageinDebug(String message) {

		String TAG = "CameraUtil";
		Log.d(TAG, "message");

	}
}