package com.android.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorDetailsActivity extends Activity implements SensorEventListener {
	
	private int sensorType;
	private SensorManager sensorManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_details);		
		Bundle data = getIntent().getExtras();		
		sensorType = data.getInt("sensorType");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		Sensor sensor = sensorManager.getDefaultSensor(sensorType);
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float[] data = event.values;
		
		TextView sensorDetailsTextView = (TextView) findViewById(R.id.sensor_details_text_view);
		String details = "";
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			
			details = "Accelerometer = X: "+data[0]+" Y: "+data[1]+" Z: "+data[2];
			
		} else if (sensorType == Sensor.TYPE_GYROSCOPE) {
			
			details = "Gyroscope = X: "+data[0]+" Y: "+data[1]+" Z: "+data[2];
			
		} else {
			details = "Value: "+data[0];
		}
		
		sensorDetailsTextView.setText(details);
		
		
		
	}
}
