package com.example.nightclubpicker.place_details;

import android.net.Uri;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.PlaceAttributeListItem;
import com.example.nightclubpicker.common.list_items.ReviewListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.nearby_places.PlaceHelper;
import com.example.nightclubpicker.models.DetailsResult;
import com.example.nightclubpicker.models.ExtendedPlace;
import com.example.nightclubpicker.models.PlaceReview;
import com.example.nightclubpicker.models.SearchResult;
import com.example.nightclubpicker.service.ExtendedPlacesService;
import com.example.nightclubpicker.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsPresenter implements PlaceDetailsContract.Presenter {

    private static final int MAX_REVIEWS = 3;

    private PlaceDetailsContract.View view;
    private SearchResult searchResult;

    private DetailsResult placeDetails;
    private ExtendedPlace extendedPlaceDetails;
    private List<ListItem> listItems;

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
                    updateRating();
                    updatePriceLevel();
//                    updateAttributes();
                    generateAttributesSection();
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
//        if (extendedPlaceDetails.getMusicGenres() != null) {
//            StringBuilder musicDescription = new StringBuilder();
//            for (int i = 0; i < extendedPlaceDetails.getMusicGenres().size(); i++) {
//                if (i == 0) {
//                    musicDescription.append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
//                } else {
//                    musicDescription.append(" • ")
//                            .append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
//                }
//            }
//            view.setMusicGenreView(musicDescription.toString());
//        }
    }

    private void generateAttributesSection() {
        listItems = new ArrayList<>();

        if (extendedPlaceDetails != null && extendedPlaceDetails.getMusicGenres() != null) {
            StringBuilder musicDescription = new StringBuilder();
            for (int i = 0; i < extendedPlaceDetails.getMusicGenres().size(); i++) {
                if (i == 0) {
                    musicDescription.append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
                } else {
                    musicDescription.append(" • ")
                            .append(extendedPlaceDetails.getMusicGenres().get(i).getMusicGenre().getDisplayString());
                }
            }

            listItems.add(new PlaceAttributeListItem.Builder()
                    .setFirstItem(true)
                    .setLabel(musicDescription.toString())
                    .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_music_note))
                    .build());
        }

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getFormattedAddress())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_map_pin))
                .setMapUrl(PlaceHelper.createUrlForStaticMap(
                        placeDetails.getGeometry().getLocation().getLatitude(),
                        placeDetails.getGeometry().getLocation().getLongitude()))
                .build());

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getOpeningHours().isOpenNow() ? ResourceSingleton.getInstance().getString(R.string.open) : ResourceSingleton.getInstance().getString(R.string.closed))
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_clock))
                .build());

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getFormattedPhoneNumber())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_phone))
                .build());

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getWebsiteUrl())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_web))
                .build());
    }

    private void generateReviewsSection() {
        if (placeDetails.getReviews() == null) {
            return;
        }

        ReviewListItem.Builder builder = new ReviewListItem.Builder();

        int reviewNum = Math.min(MAX_REVIEWS, placeDetails.getReviews().size());
        for (int i = 0; i < reviewNum; i++) {
            PlaceReview placeReview = placeDetails.getReviews().get(i);
            if (placeReview != null) {
                listItems.add(builder.setProfilePictureUrl(placeReview.getProfilePhotoUrl())
                        .setName(placeReview.getAuthorName())
                        .setRating(placeReview.getRating())
                        .setRelativeTime(placeReview.getRelativeTimeDescription())
                        .setContent(placeReview.getText())
                        .build());
            }
        }

        view.updateListItems(listItems);
    }

    private void updateRating() {
        view.setStarRating(placeDetails.getRating(), placeDetails.getUserRatingsTotal());
    }

    private void updatePriceLevel() {
        view.setPriceLevel(placeDetails.getPriceLevel(), PlaceHelper.generatePriceLevelString(placeDetails.getPriceLevel()));
    }
}
