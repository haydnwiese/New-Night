package com.example.nightclubpicker.places.place_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.places.models.DetailsResult;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.places.service.PlacesService;

import static com.example.nightclubpicker.places.NearbyPlacesListActivity.BUNDLE_KEY_SEARCH_RESULT;

public class PlaceDetailsActivity extends BaseActivity {

    private SearchResult searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Intent intent = getIntent();
        if (intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT) != null) {
            searchResult = intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT);
            setTitle(searchResult.getName());
        }
    }
}
