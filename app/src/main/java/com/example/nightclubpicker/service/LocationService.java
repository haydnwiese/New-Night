package com.example.nightclubpicker.service;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationService implements LocationListener {

    private LocationManager locationManager;
    private LocationRequestCallback callback;

    public interface LocationRequestCallback {
        void onLocationUpdated(Location location);
    }

    public LocationService(LocationManager locationManager, LocationRequestCallback callback) {
        this.locationManager = locationManager;
        this.callback = callback;
    }

    @SuppressLint("MissingPermission")
    public void fetchLocation() {
        Log.d("LocationService", "Fetching Location");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationService", "Location updated");
        callback.onLocationUpdated(location);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
