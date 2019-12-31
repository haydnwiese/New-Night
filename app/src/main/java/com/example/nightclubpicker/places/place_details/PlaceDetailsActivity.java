package com.example.nightclubpicker.places.place_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.places.models.DetailsResult;
import com.example.nightclubpicker.places.models.Photo;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.places.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nightclubpicker.places.NearbyPlacesListActivity.BUNDLE_KEY_SEARCH_RESULT;

public class PlaceDetailsActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private DetailsResult placeDetails;
    private List<Photo> photos = new ArrayList<>();

    ImageViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        setTitle(R.string.details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT) != null) {
            SearchResult searchResult = intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT);
            fetchPlaceDetails(searchResult.getPlaceId());
        }
    }

    private void fetchPlaceDetails(String placeId) {
        new PlacesService().fetchPlaceDetails(placeId, new PlacesService.PlaceDetailsCallback() {
            @Override
            public void onSuccess(DetailsResult response) {
                photos = response.getPhotos();
                viewPagerAdapter = new ImageViewPagerAdapter(PlaceDetailsActivity.this, photos);
                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
