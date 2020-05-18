package com.example.nightclubpicker.nearby_places;

import android.location.Location;
import android.location.LocationManager;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.ExtraResultListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.SpinnerListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.list_items.TopResultListItem;
import com.example.nightclubpicker.nearby_places.models.ExtendedPlace;
import com.example.nightclubpicker.nearby_places.models.NearbySearchResponse;
import com.example.nightclubpicker.nearby_places.models.PlaceType;
import com.example.nightclubpicker.nearby_places.models.SearchResult;
import com.example.nightclubpicker.nearby_places.service.ExtendedPlacesService;
import com.example.nightclubpicker.nearby_places.service.LocationService;
import com.example.nightclubpicker.nearby_places.service.PlacesService;
import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NearbyPlacesPresenter implements PlacesContract.Presenter {
    private static final int MAX_TOP_RESULTS = 3;

    private PlacesContract.View view;
    private LocationManager locationManager;
    private ExtendedPlacesService extendedPlacesService = new ExtendedPlacesService();
    private PlacesService placesService = new PlacesService();

    private HashSet<String> extendedPlacesFiltered = new HashSet<>();
    private List<ListItem> listItems = new ArrayList<>();

    private Location currentLocation;
    private MusicGenre musicGenre;
    private VenueSize venueSize;
    private DressCode dressCode;
    private int radius;
    private PlaceType placeType;
    private String nextPageToken;

    public NearbyPlacesPresenter(PlacesContract.View view,
                                 LocationManager locationManager,
                                 MusicGenre musicGenre,
                                 VenueSize venueSize,
                                 DressCode dressCode,
                                 int radius,
                                 PlaceType placeType) {
        this.view = view;
        this.locationManager = locationManager;
        this.musicGenre = musicGenre;
        this.venueSize = venueSize;
        this.dressCode = dressCode;
        this.radius = radius;
        this.placeType = placeType;
    }

    @Override
    public void onStart() {
        fetchLocation();
    }

    @Override
    public void fetchLocation() {
        if (view.hasLocationPermission()) {
            new LocationService(locationManager, (location) -> {
                currentLocation = location;
                fetchExtendedPlaces();
            }).fetchLocation();
        }
    }

    private void fetchExtendedPlaces() {
        extendedPlacesService.fetchFilteredPlaces(dressCode,
                musicGenre,
                venueSize,
                new ExtendedPlacesService.ExtendedPlacesListCallback() {
                    @Override
                    public void onSuccess(List<ExtendedPlace> extendedPlacesList) {
                        if (extendedPlacesList != null) {
                            for (ExtendedPlace extendedPlace : extendedPlacesList) {
                                if (extendedPlace != null && extendedPlace.getGoogleId() != null) {
                                    extendedPlacesFiltered.add(extendedPlace.getGoogleId());
                                }
                            }
                            fetchPlaces();
                        }
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    private void fetchPlaces() {
        placesService.fetchNearbyPlaces(currentLocation.getLatitude(),
                currentLocation.getLongitude(),
                radius,
                null,
                placeType,
                new PlacesService.NearbySearchCallback() {
                    @Override
                    public void onSuccess(NearbySearchResponse response) {
                        if (response.getResults() != null) {
                            view.setLoadingSpinnerVisibility(false);
                            nextPageToken = response.getNextPageToken();
                            createSearchResultListItems(response.getResults());
                        }
                    }

                    @Override
                    public void onFailure() {
//                        Toast.makeText(NearbyPlacesActivity.this, "Failed", Toast.LENGTH_SHORT)
//                                .show();
                    }
                });
    }

    private void createSearchResultListItems(List<SearchResult> searchResults) {
        listItems = new ArrayList<>();

        listItems.add(new SubHeaderListItem.Builder()
                .setSubHeader(ResourceSingleton.getInstance().getString(R.string.top_results))
                .build());

        for (int i = 0; i < searchResults.size(); i++) {
            SearchResult result = searchResults.get(i);
            if (
                    !result.isPermanentlyClosed()
                            && result.getRating() != 0) {
                if (i < MAX_TOP_RESULTS) {
                    String photoReference = null;
                    if (result.getPhotos() != null && result.getPhotos().get(0) != null) {
                        photoReference = result.getPhotos().get(0).getPhotoReference();
                    }
                    listItems.add(new TopResultListItem.Builder()
                            .setImageUrl(photoReference)
                            .setName(result.getName())
                            .setRating(result.getRating())
                            .setReviewCount(result.getUserRatingsTotal())
                            .setPriceLevel(result.getPriceLevel())
                            .setDistance(getDistanceToTarget(result))
                            .setClickListener(() -> view.navigateToPlaceDetails(result))
                            .build());
                } else {
                    listItems.add(generateExtraResultListItem(result));
                }
            }
        }

        if (listItems.size() > MAX_TOP_RESULTS) {
            listItems.add(MAX_TOP_RESULTS + 1, new SubHeaderListItem.Builder()
                    .setSubHeader(ResourceSingleton.getInstance().getString(R.string.more_results))
                    .build());
        }

        view.updateListItems(listItems);
    }

    private ListItem generateExtraResultListItem(SearchResult result) {
        String photoReference = null;
        if (result.getPhotos() != null && result.getPhotos().get(0) != null) {
            photoReference = result.getPhotos().get(0).getPhotoReference();
        }
        return new ExtraResultListItem.Builder()
                .setImageUrl(photoReference)
                .setName(result.getName())
                .setRating(result.getRating())
                .setReviewCount(result.getUserRatingsTotal())
                .setPriceLevel(result.getPriceLevel())
                .setDistance(getDistanceToTarget(result))
                .setClickListener(() -> view.navigateToPlaceDetails(result))
                .build();
    }

    @Override
    public void loadMoreResults() {
        listItems.add(new SpinnerListItem());
        view.notifyListInsertion(listItems.size() - 1);

        if (nextPageToken != null) {
            placesService.fetchAdditionalNearbyPlaces(nextPageToken, new PlacesService.NearbySearchCallback() {
                @Override
                public void onSuccess(NearbySearchResponse response) {
                    nextPageToken = response.getNextPageToken();
                    if (response.getResults() != null) {
                        addResultsToList(response.getResults());
                        view.setLoading(false);
                    } else {
                        removeSpinnerListItem();
                    }
                }

                @Override
                public void onFailure() {
                    // TODO: Fix this
                    removeSpinnerListItem();
                }
            });
        } else {
            removeSpinnerListItem();
        }
    }

    private void addResultsToList(List<SearchResult> results) {
        if (listItems != null && !results.isEmpty()) {
            listItems.set(listItems.size() - 1, generateExtraResultListItem(results.get(0)));
            for (int i = 1; i < results.size(); i++) {
                SearchResult result = results.get(i);
                if (!result.isPermanentlyClosed() && result.getRating() != 0.0) {
                    listItems.add(generateExtraResultListItem(result));
                }
            }
            view.updateListItems(listItems);
        }
    }

    private void removeSpinnerListItem() {
        listItems.remove(listItems.size() - 1);
    }

    @Override
    public int getListSize() {
        return listItems.size();
    }

    private float getDistanceToTarget(SearchResult result) {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(result.getGeometry().getLocation().getLatitude());
        targetLocation.setLongitude(result.getGeometry().getLocation().getLongitude());
        return currentLocation.distanceTo(targetLocation) / 1000f;
    }
}
