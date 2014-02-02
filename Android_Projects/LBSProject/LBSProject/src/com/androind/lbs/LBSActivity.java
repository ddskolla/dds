package com.androind.lbs;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LBSActivity extends MapActivity implements LocationListener {

	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		 //Explicitly mentioning the type of the location provider
		LocationProvider gpsLocationProvider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);
		
		
		locationManager.requestLocationUpdates(
				gpsLocationProvider.getName(), 0, 0, this);
		
		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		String providerName = locationManager.getBestProvider(criteria, false);

		locationManager.requestLocationUpdates(providerName, 0, 0, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		// Explicitly mentioning the type of the location provider
		LocationProvider networkLocationProvider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);

		locationManager.requestLocationUpdates(
				networkLocationProvider.getName(), 60*1000,  1000, this);
	}

	@Override
	public void onLocationChanged(Location location) {

		TextView locationDetailsTextView = (TextView) findViewById(R.id.location_details_text_view);

		String locationDetails = "Longitude: " + location.getLongitude()
				+ " Latitude: " + location.getLatitude() + "Accuracy: "
				+ location.getAccuracy();

		locationDetailsTextView.setText(locationDetails);

		Geocoder geocoder = new Geocoder(this);
		try {
			List<Address> addresses = geocoder.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);

			if (addresses != null && addresses.size() == 1) {
				Address currentAddress = addresses.get(0);

				String addressDetails = "";

				for (int i = 0; i < currentAddress.getMaxAddressLineIndex(); i++) {

					addressDetails += currentAddress.getAddressLine(i);

				}

				TextView addressDetailsTextView = (TextView) findViewById(R.id.address_details_text_view);
				addressDetailsTextView.setText(addressDetails);

				MapView mapView = (MapView) findViewById(R.id.map_view);
				mapView.setBuiltInZoomControls(true);
				mapView.setSatellite(true);

				MapController mapController = mapView.getController();
				mapController.setZoom(25);

				GeoPoint geoPoint = new GeoPoint(
						(int) (location.getLatitude() * 1E6),
						(int) (location.getLongitude() * 1E6));

				mapController.animateTo(geoPoint);
				
				mapView.getOverlays().add(new PushPinOverlay(geoPoint));

			}

		} catch (Exception e) {

		}

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private class PushPinOverlay extends Overlay {

		private GeoPoint geoPoint;

		public PushPinOverlay(GeoPoint geoPoint) {
			this.geoPoint = geoPoint;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			Point p = new Point();
			mapView.getProjection().toPixels(geoPoint, p);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.pushpin);
			canvas.drawBitmap(bmp, p.x, p.y - 64, null);

		}
	}

}