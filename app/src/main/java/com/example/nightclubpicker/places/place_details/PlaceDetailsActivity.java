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
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.views.HeaderListItemWrapperView;
import com.example.nightclubpicker.common.views.PlaceAttributeView;
import com.example.nightclubpicker.common.views.StarRatingView;
import com.example.nightclubpicker.common.views.SubHeaderListItemWrapperView;
import com.example.nightclubpicker.places.PlaceHelper;
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
    @BindView(R.id.dot)
    TextView dotSeparatorView;
    @BindView(R.id.priceIndicator)
    TextView priceLevelView;
    @BindView(R.id.musicGenre)
    PlaceAttributeView musicGenreView;
    @BindView(R.id.address)
    PlaceAttributeView addressView;
    @BindView(R.id.openHours)
    PlaceAttributeView openHoursView;
    @BindView(R.id.phoneNumber)
    PlaceAttributeView phoneNumberView;
    @BindView(R.id.website)
    PlaceAttributeView websiteView;
    @BindView(R.id.recentReviewsHeader)
    SubHeaderListItemWrapperView recentReviewsHeaderView;

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

                    headerWrapperView.setItems(new HeaderListItem(response.getName()));
                    recentReviewsHeaderView.setItems(new SubHeaderListItem(getString(R.string.recentReviewsHeader)));
                    updateRating();
                    updatePriceLevel();
                    updateAttributes();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void updateRating() {
        reviewCountView.setText(getString(R.string.review_count, placeDetails.getUserRatingsTotal()));
        starRatingView.setRating(placeDetails.getRating());
        exactRatingView.setText(Double.toString(placeDetails.getRating()));
    }

    private void updatePriceLevel() {
        dotSeparatorView.setVisibility(placeDetails.getPriceLevel() == 0 ? View.GONE : View.VISIBLE);
        priceLevelView.setText(PlaceHelper.generatePriceLevelString(placeDetails.getPriceLevel()));
    }

    private void updateAttributes() {
        // TODO: Update with real values
        musicGenreView.setDescription("Hip-Hop/Rap");
        // TODO: Add map image
        addressView.setDescription(placeDetails.getFormattedAddress());
        // TODO: Update with formatted value
        openHoursView.setDescription(Boolean.toString(placeDetails.getOpeningHours().isOpenNow()));
        phoneNumberView.setDescription(placeDetails.getFormattedPhoneNumber());
        websiteView.setDescription(placeDetails.getWebsiteUrl());
    }
}
