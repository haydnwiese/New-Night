package com.example.nightclubpicker.place_details;

import android.net.Uri;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.ReviewListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.nearby_places.PlaceHelper;
import com.example.nightclubpicker.nearby_places.models.DetailsResult;
import com.example.nightclubpicker.nearby_places.models.ExtendedPlace;
import com.example.nightclubpicker.nearby_places.models.PlaceReview;
import com.example.nightclubpicker.nearby_places.models.SearchResult;
import com.example.nightclubpicker.nearby_places.service.ExtendedPlacesService;
import com.example.nightclubpicker.nearby_places.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsPresenter implements PlaceDetailsContract.Presenter {

    private static final int MAX_REVIEWS = 3;

    private PlaceDetailsContract.View view;
    private SearchResult searchResult;

    private DetailsResult placeDetails;
    private ExtendedPlace extendedPlaceDetails;
    private List<ListItem> reviewItems;

    PlaceDetailsPresenter(PlaceDetailsContract.View view, SearchResult searchResult) {
        this.view = view;
        this.searchResult = searchResult;
    }

    @Override
    public void onViewCreated() {
        fetchExtendedPlaceDetails(searchResult.getPlaceId());
        fetchPlaceDetails(searchResult.getPlaceId());
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
                    view.initViewPager(response.getPhotos());

                    view.setHeaderWrapperView(new HeaderListItem(response.getName()));
                    view.setRecentReviewsHeaderView(new SubHeaderListItem(ResourceSingleton.getInstance().getString(R.string.recentReviewsHeader)));
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

    private void updateExtendedPlaceDetails() {
        if (extendedPlaceDetails.getSize() != null) {
            view.setVenueSizeView(extendedPlaceDetails.getSize().getDetailsDisplayString());
        }
        if (extendedPlaceDetails.getDressCode() != null) {
            view.setDressCodeView(extendedPlaceDetails.getDressCode().getDisplayString());
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
            view.setMusicGenreView(musicDescription.toString());
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

        view.updateReviewListItems(reviewItems);
    }

    private void updateRating() {
        view.setStarRating(placeDetails.getRating(), placeDetails.getUserRatingsTotal());
    }

    private void updatePriceLevel() {
        view.setPriceLevel(placeDetails.getPriceLevel(), PlaceHelper.generatePriceLevelString(placeDetails.getPriceLevel()));
    }

    private void updateAttributes() {
        view.setAddressView(placeDetails.getFormattedAddress());
        loadStaticMap();
        // TODO: Add dropdown for weekly hours
        view.setOpenHoursView(placeDetails.getOpeningHours().isOpenNow() ? ResourceSingleton.getInstance().getString(R.string.open) : ResourceSingleton.getInstance().getString(R.string.closed));
        view.setPhoneNumberView(placeDetails.getFormattedPhoneNumber());
        view.setWebsiteView(placeDetails.getWebsiteUrl());
    }

    private void loadStaticMap() {
        Uri url = PlaceHelper.createUrlForStaticMap(placeDetails.getGeometry().getLocation().getLatitude(), placeDetails.getGeometry().getLocation().getLongitude());
        view.loadStaticMap(url);
    }
}
