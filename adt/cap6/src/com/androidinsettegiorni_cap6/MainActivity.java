package com.androidinsettegiorni_cap6;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buildGoogleApiClient();
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			// mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
			// mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		mLastLocation = location;
		// mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
		updateUI();
	}

	private void updateUI() {
		// mLatitudeTextView.setText(String.valueOf(mLastLocation.getLatitude()));
		// mLongitudeTextView.setText(String.valueOf(mLastLocation.getLongitude()));
		// mLastUpdateTimeTextView.setText(mLastUpdateTime);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	public void onConnectionSuspended(int cause) {

	}

	protected void createLocationRequest() {
		LocationRequest mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(10000);
		mLocationRequest.setFastestInterval(5000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mGoogleApiClient.isConnected()) {
			// startLocationUpdates();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// stopLocationUpdates();
	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

}
