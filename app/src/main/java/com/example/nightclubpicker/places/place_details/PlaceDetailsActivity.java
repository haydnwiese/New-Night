package com.example.nightclubpicker.places.place_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.ReviewListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.views.HeaderListItemWrapperView;
import com.example.nightclubpicker.common.views.PlaceAttributeView;
import com.example.nightclubpicker.common.views.StarRatingView;
import com.example.nightclubpicker.common.views.SubHeaderListItemWrapperView;
import com.example.nightclubpicker.places.PlaceHelper;
import com.example.nightclubpicker.places.models.DetailsResult;
import com.example.nightclubpicker.places.models.Photo;
import com.example.nightclubpicker.places.models.PlaceReview;
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
    @BindView(R.id.reviewsRecyclerView)
    RecyclerView reviewsRecyclerView;

    private static final int MAX_REVIEWS = 3;

    private DetailsResult placeDetails;
    private List<Photo> photos = new ArrayList<>();
    private List<ListItem> reviewItems;

    ImageViewPagerAdapter viewPagerAdapter;
    CommonListItemAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        setTitle(R.string.details);
        ButterKnife.bind(this);

        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new CommonListItemAdapter(new ArrayList<>());
        reviewsRecyclerView.setAdapter(recyclerViewAdapter);

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
                    generateReviewsSection();
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
        if (placeDetails.getOpeningHours() != null) {
            openHoursView.setDescription(Boolean.toString(placeDetails.getOpeningHours().isOpenNow()));
        } else {
            openHoursView.setVisibility(View.GONE);
        }
        if (placeDetails.getFormattedPhoneNumber() != null) {
            phoneNumberView.setDescription(placeDetails.getFormattedPhoneNumber());
        } else {
            phoneNumberView.setVisibility(View.GONE);
        }
        if (placeDetails.getWebsiteUrl() != null) {
            websiteView.setDescription(placeDetails.getWebsiteUrl());
        } else {
            websiteView.setVisibility(View.GONE);
        }
    }

    private void generateReviewsSection() {
        if (placeDetails.getReviews() == null) {
            return;
        }

        reviewItems = new ArrayList<>();
        ReviewListItem.Builder builder = new ReviewListItem.Builder();

        int reviewNum = Math.min(MAX_REVIEWS, placeDetails.getReviews().size());
        for (int i = 0; i < reviewNum; i++) {
            PlaceReview placeReview = placeDetails.getReviews().get(i);
            if (placeReview != null) {
                reviewItems.add(builder.setProfilePictureUrl(placeReview.getProfilePhotoUrl())
                        .setName(placeReview.getAuthorName())
                        .setRating(placeReview.getRating())
                        .setRelativeTime(placeReview.getRelativeTimeDescription())
                        .setContent(placeReview.getText())
                        .build());
            }
        }

        recyclerViewAdapter.setListItems(reviewItems);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
