package com.example.nightclubpicker.onboarding_flow;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.places.NearbyPlacesListActivity;

public class FilterSelectionActivity extends BaseActivity {

    public static final String BUNDLE_LAT = "bundleLatitude";
    public static final String BUNDLE_LNG = "bundleLongitude";

    private Location location;
    private LocationManager locationManager;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_selection);
        setTitle(R.string.select_filters);
    }

    public void navigateToResults(View view) {
        Intent intent = new Intent(this, NearbyPlacesListActivity.class);
        startActivity(intent);
    }
}
