package com.example.nightclubpicker.places.place_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.views.HeaderListItemWrapperView;
import com.example.nightclubpicker.common.views.StarRatingView;
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
    @BindView(R.id.header)
    HeaderListItemWrapperView headerWrapperView;
    @BindView(R.id.starRatingView)
    StarRatingView starRatingView;
    @BindView(R.id.exactStarRating)
    TextView exactRatingView;
    @BindView(R.id.numberOfReviews)
    TextView reviewCountView;

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
                if (response != null) {
                    placeDetails = response;
                    if (response.getPhotos() != null) {
                        photos = response.getPhotos().subList(0, Math.min(response.getPhotos().size(), 4));
                        viewPagerAdapter = new ImageViewPagerAdapter(PlaceDetailsActivity.this, photos);
                        viewPager.setAdapter(viewPagerAdapter);
                    } else {
                        viewPager.setVisibility(View.GONE);
                    }
                    updateRating();
                    headerWrapperView.setItems(new HeaderListItem(response.getName()));
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void updateRating() {
        reviewCountView.setText(ResourceSingleton.getInstance().getString(R.string.review_count, placeDetails.getUserRatingsTotal()));
        starRatingView.setRating(placeDetails.getRating());
        exactRatingView.setText(Double.toString(placeDetails.getRating()));
    }
}
