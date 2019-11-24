package com.example.nightclubpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.places.NearbyPlacesListActivity;

public class MainActivity extends BaseActivity implements LocationListener {

    public static final String BUNDLE_LAT = "bundleLatitude";
    public static final String BUNDLE_LNG = "bundleLongitude";

    private Location location;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation();
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void navigateToResults(View view) {
        Intent intent = new Intent(this, NearbyPlacesListActivity.class);
        intent.putExtra(BUNDLE_LAT, location.getLatitude());
        intent.putExtra(BUNDLE_LNG, location.getLongitude());
        startActivity(intent);
    }

    @Override
    protected void setAdditionalActionBarProperties() {
        this.getSupportActionBar().hide();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        locationTextView = findViewById(R.id.locationTextView);
        String locationStr = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
        locationTextView.setText(locationStr);
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
