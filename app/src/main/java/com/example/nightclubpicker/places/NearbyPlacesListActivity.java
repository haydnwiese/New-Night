package com.example.nightclubpicker.places;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.adapters.DividerItemDecoration;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.places.models.PlaceType;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.places.place_details.PlaceDetailsActivity;
import com.example.nightclubpicker.places.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyPlacesListActivity extends BaseActivity implements LocationListener {
    public static final String BUNDLE_KEY_SEARCH_RESULT = "bundleKeySearchResult";

    @BindView(R.id.loadingSpinner)
    ProgressBar loadingSpinner;
    @BindView(R.id.resultsRecyclerView)
    RecyclerView recyclerView;

    private CommonListItemAdapter adapter;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_list);
        setTitle(R.string.results);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(ResourceSingleton.getInstance().getDrawable(R.drawable.divider)));
        adapter = new CommonListItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        getLocation();
    }

    private void navigateToPlaceDetails(SearchResult result) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(BUNDLE_KEY_SEARCH_RESULT, result);
        startActivity(intent);
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

    private void fetchPlaces(double lat, double lng) {
        PlacesService placesService = new PlacesService();
        placesService.fetchNearbyPlaces(lat,
                lng,
                50000,
                null,
                PlaceType.night_club,
                new PlacesService.NearbySearchCallback() {
                    @Override
                    public void onSuccess(List<SearchResult> searchResults) {
                        if (searchResults != null) {
                            loadingSpinner.setVisibility(View.GONE);
                            createSearchResultListItems(searchResults);
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(NearbyPlacesListActivity.this, "Failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void createSearchResultListItems(List<SearchResult> searchResults) {
        List<ListItem> listItems = new ArrayList<>();

        listItems.add(new SubHeaderListItem.Builder()
                .setSubHeader(ResourceSingleton.getInstance().getString(R.string.top_results))
                .build());

        for (SearchResult result : searchResults) {
            listItems.add(new ResultListItem.Builder()
                    .setImageUrl(result.getPhotos().get(0).getPhotoReference())
                    .setName(result.getName())
                    .setClickListener(() -> navigateToPlaceDetails(result))
                    .build());
        }

        Toast.makeText(NearbyPlacesListActivity.this, "Success", Toast.LENGTH_SHORT)
                .show();

        adapter.setListItems(listItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();
        fetchPlaces(location.getLatitude(), location.getLongitude());
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
