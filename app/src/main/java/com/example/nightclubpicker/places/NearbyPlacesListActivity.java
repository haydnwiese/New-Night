package com.example.nightclubpicker.places;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.places.models.PlaceType;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.places.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nightclubpicker.MainActivity.BUNDLE_LAT;
import static com.example.nightclubpicker.MainActivity.BUNDLE_LNG;

public class NearbyPlacesListActivity extends AppCompatActivity {

    @BindView(R.id.resultsRecyclerView)
    RecyclerView recyclerView;

    ResultsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_list);
        ButterKnife.bind(this);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResultsViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        fetchPlaces(intent.getDoubleExtra(BUNDLE_LAT, 0), intent.getDoubleExtra(BUNDLE_LNG, 0));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
                        Toast.makeText(NearbyPlacesListActivity.this, "Success", Toast.LENGTH_SHORT)
                                .show();
                        adapter.setListItems(searchResults);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(NearbyPlacesListActivity.this, "Failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
