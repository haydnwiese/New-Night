package com.example.nightclubpicker.place_details;

import android.content.Intent;
import android.net.Uri;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.OpenHoursListItem;
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

import java.net.URLEncoder;
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

        // TODO: Remove when using extended places api
        listItems.add(new PlaceAttributeListItem.Builder()
                .setFirstItem(true)
                .setLabel("Dance")
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_music_note))
                .build());

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getFormattedAddress())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_map_pin))
                .setMapUrl(PlaceHelper.createUrlForStaticMap(
                        placeDetails.getGeometry().getLocation().getLatitude(),
                        placeDetails.getGeometry().getLocation().getLongitude()))
                .setClickListener(() -> {
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", URLEncoder.encode(placeDetails.getFormattedAddress()))));
                    view.navigate(mapIntent);
                })
                .build());

        if (placeDetails.getOpeningHours() != null && placeDetails.getOpeningHours().getWeekdayText() != null) {
            String[] weekdayText = new String[placeDetails.getOpeningHours().getWeekdayText().size()];
            placeDetails.getOpeningHours().getWeekdayText().toArray(weekdayText);
            listItems.add(new OpenHoursListItem.Builder()
                    .setOpen(placeDetails.getOpeningHours().isOpenNow())
                    .setWeekdayText(weekdayText)
                    .build());
        }

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getFormattedPhoneNumber())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_phone))
                .setClickListener(() -> {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + placeDetails.getFormattedPhoneNumber()));
                    view.navigate(phoneIntent);
                })
                .build());

        listItems.add(new PlaceAttributeListItem.Builder()
                .setLabel(placeDetails.getWebsiteUrl())
                .setIcon(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_web))
                .setClickListener(() -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeDetails.getWebsiteUrl()));
                    view.navigate(browserIntent);
                })
                .build());
    }

    private void generateReviewsSection() {
        if (placeDetails.getReviews() == null) {
            return;
        }

        listItems.add(new SubHeaderListItem.Builder()
                .setSubHeader(ResourceSingleton.getInstance().getString(R.string.recentReviewsHeader))
                .setHasLeftMargin(false)
                .build());

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
