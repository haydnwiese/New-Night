package com.example.nightclubpicker.place_details;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.nightclubpicker.nearby_places.PlaceHelper;
import com.example.nightclubpicker.nearby_places.models.DetailsResult;
import com.example.nightclubpicker.nearby_places.models.ExtendedPlace;
import com.example.nightclubpicker.nearby_places.models.Photo;
import com.example.nightclubpicker.nearby_places.models.PlaceReview;
import com.example.nightclubpicker.nearby_places.models.SearchResult;
import com.example.nightclubpicker.nearby_places.service.ExtendedPlacesService;
import com.example.nightclubpicker.nearby_places.service.PlacesService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nightclubpicker.nearby_places.NearbyPlacesActivity.BUNDLE_KEY_SEARCH_RESULT;

public class PlaceDetailsActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.dotsView)
    LinearLayout dotsLayout;
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
    @BindView(R.id.venueSize)
    TextView venueSizeView;
    @BindView(R.id.dressCode)
    TextView dressCodeView;
    @BindView(R.id.musicGenre)
    PlaceAttributeView musicGenreView;
    @BindView(R.id.address)
    PlaceAttributeView addressView;
    @BindView(R.id.staticMap)
    ImageView staticMapView;
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
    private ExtendedPlace extendedPlaceDetails;
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
            fetchExtendedPlaceDetails(searchResult.getPlaceId());
        }
    }

    private void fetchExtendedPlaceDetails(String placeId) {
        new ExtendedPlacesService().fetchExtendedPlaceById(placeId, new ExtendedPlacesService.ExtendedPlacesCallback() {
            @Override
            public void onSuccess(ExtendedPlace extendedPlace) {
                if (extendedPlace != null) {
                    extendedPlaceDetails = extendedPlace;
                    updateExtendedPlaceDetails();
                }
            }

            @Override
            public void onFailure() {

            }
        });
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
                        addViewPagerDots(viewPagerAdapter.getCount(), 0);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                addViewPagerDots(viewPagerAdapter.getCount(), position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
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

    private void addViewPagerDots(int size, int current) {
        if (size <= 1) {
            return;
        }

        ImageView[] dots  = new ImageView[size];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.indicator_dot_dark);
            dotsLayout.addView(dots[i]);
        }
        dots[current].setImageResource(R.drawable.indicator_dot_light);
    }

    private void updateExtendedPlaceDetails() {
        if (extendedPlaceDetails.getSize() != null) {
            venueSizeView.setText(extendedPlaceDetails.getSize().getDetailsDisplayString());
        }
        if (extendedPlaceDetails.getDressCode() != null) {
            dressCodeView.setText(extendedPlaceDetails.getDressCode().getDisplayString());
        }
        if (extendedPlaceDetails.getMusicGenres() != null) {
            StringBuilder musicDescription = new StringBuilder();
            for (int i = 0; i < extendedPlaceDetails.getMusicGenres().size(); i++) {
                if (i == 0) {
                    musicDescription.append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
                } else {
                    musicDescription.append(" • ")
                            .append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
                }
            }
            musicGenreView.setDescription(musicDescription.toString());
        }
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
        addressView.setDescription(placeDetails.getFormattedAddress());
        loadStaticMap();
        // TODO: Add dropdown for weekly hours
        if (placeDetails.getOpeningHours() != null) {
            openHoursView.setDescription(placeDetails.getOpeningHours().isOpenNow() ? ResourceSingleton.getInstance().getString(R.string.open) : ResourceSingleton.getInstance().getString(R.string.closed));
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

    private void loadStaticMap() {
        Uri url = PlaceHelper.createUrlForStaticMap(placeDetails.getGeometry().getLocation().getLatitude(), placeDetails.getGeometry().getLocation().getLongitude());
        Picasso.get()
                .load(url)
                .fit()
                .into(staticMapView);
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
