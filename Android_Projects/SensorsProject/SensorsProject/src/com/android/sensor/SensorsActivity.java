package com.android.sensor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SensorsActivity extends Activity implements OnItemClickListener {
	
	private SensorManager sensorManager;
	private List<Sensor> sensors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView sensorsListView = (ListView) findViewById(R.id.sensors_list_view);
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        
        List<String> sensorNames = new ArrayList<String>();
        
        for (Sensor sensor: sensors) {
        	sensorNames.add(sensor.getName());
        }
        
        ListAdapter adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, sensorNames);
        
        sensorsListView.setAdapter(adapter);
        sensorsListView.setOnItemClickListener(this);
        
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int selectedIndex, long arg3) {
	
		Sensor sensor = sensors.get(selectedIndex);
		Intent intent =new Intent(this, SensorDetailsActivity.class);
		
		Bundle data = new Bundle();
		data.putInt("sensorType", sensor.getType());
		intent.putExtras(data);		
		startActivity(intent);
		
		
	}
}