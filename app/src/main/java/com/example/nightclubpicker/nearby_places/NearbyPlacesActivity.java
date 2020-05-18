package com.example.nightclubpicker.nearby_places;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.list_items.ExtraResultListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.SpinnerListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.list_items.TopResultListItem;
import com.example.nightclubpicker.nearby_places.service.LocationService;
import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;
import com.example.nightclubpicker.nearby_places.models.ExtendedPlace;
import com.example.nightclubpicker.nearby_places.models.NearbySearchResponse;
import com.example.nightclubpicker.nearby_places.models.PlaceType;
import com.example.nightclubpicker.nearby_places.models.SearchResult;
import com.example.nightclubpicker.place_details.PlaceDetailsActivity;
import com.example.nightclubpicker.nearby_places.service.ExtendedPlacesService;
import com.example.nightclubpicker.nearby_places.service.PlacesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyPlacesActivity extends BaseActivity implements PlacesContract.View {
    public static final String BUNDLE_KEY_SEARCH_RESULT = "bundleKeySearchResult";
    private static final String BUNDLE_KEY_RADIUS = "bundleKeyRadius";
    private static final String BUNDLE_KEY_DRESS_CODE = "bundleKeyDressCode";
    private static final String BUNDLE_KEY_MUSIC_GENRE = "bundleKeyMusicGenre";
    private static final String BUNDLE_KEY_PLACE_TYPE = "bundleKeyPlaceType";
    private static final String BUNDLE_KEY_VENUE_SIZE = "bundleKeyVenueSize";

    private static final int MAX_TOP_RESULTS = 3;
    private static final int DEFAULT_SEARCH_RADIUS = 50;
    private static final int KM_TO_M_CONVERSION_FACTOR = 1000;

    public static Bundle getNavBundle(int radius,
                                      DressCode dressCode,
                                      MusicGenre musicGenre,
                                      com.example.nightclubpicker.onboarding_flow.models.PlaceType placeType,
                                      VenueSize venueSize) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_RADIUS, radius);
        bundle.putInt(BUNDLE_KEY_DRESS_CODE, dressCode.ordinal());
        bundle.putInt(BUNDLE_KEY_MUSIC_GENRE, musicGenre.ordinal());
        bundle.putInt(BUNDLE_KEY_PLACE_TYPE, placeType.ordinal());
        bundle.putInt(BUNDLE_KEY_VENUE_SIZE, venueSize.ordinal());
        return bundle;
    }

    @BindView(R.id.loadingSpinner)
    ProgressBar loadingSpinner;
    @BindView(R.id.resultsRecyclerView)
    RecyclerView recyclerView;

    private List<ListItem> listItems = new ArrayList<>();
    private HashSet<String> extendedPlacesFiltered = new HashSet<>();

    private PlacesContract.Presenter presenter;
    private CommonListItemAdapter adapter;
    private LocationManager locationManager;
    private String nextPageToken;
    private Location currentLocation;
    private PlacesService placesService = new PlacesService();
    private ExtendedPlacesService extendedPlacesService = new ExtendedPlacesService();
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_list);
        setTitle(R.string.results);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonListItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        presenter = new NearbyPlacesPresenter(this);

        initScrollListener();

        getLocation();
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == listItems.size() - 1) {
                    isLoading = true;
                    loadMoreResults();
                }
            }
        });
    }

    private void loadMoreResults() {
        listItems.add(new SpinnerListItem());
        adapter.notifyItemInserted(listItems.size() - 1);

        if (nextPageToken != null) {
            placesService.fetchAdditionalNearbyPlaces(nextPageToken, new PlacesService.NearbySearchCallback() {
                @Override
                public void onSuccess(NearbySearchResponse response) {
                    nextPageToken = response.getNextPageToken();
                    if (response.getResults() != null) {
                        addResultsToList(response.getResults());
                        isLoading = false;
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

    private void navigateToPlaceDetails(SearchResult result) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(BUNDLE_KEY_SEARCH_RESULT, result);
        startActivity(intent);
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        new LocationService(locationManager, this::onLocationChanged).fetchLocation();
    }

    private void fetchExtendedPlaces() {
        Intent filterData = getIntent();
        MusicGenre musicGenre = MusicGenre.values()[filterData.getIntExtra(BUNDLE_KEY_MUSIC_GENRE, 0)];
        VenueSize venueSize = VenueSize.values()[filterData.getIntExtra(BUNDLE_KEY_VENUE_SIZE, 0)];
        DressCode dressCode = DressCode.values()[filterData.getIntExtra(BUNDLE_KEY_DRESS_CODE, 0)];

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
        Intent filterData = getIntent();
        int radius = filterData.getIntExtra(BUNDLE_KEY_RADIUS, DEFAULT_SEARCH_RADIUS) * KM_TO_M_CONVERSION_FACTOR;
        PlaceType placeType = PlaceType.values()[filterData.getIntExtra(BUNDLE_KEY_PLACE_TYPE, 0)];

        placesService.fetchNearbyPlaces(currentLocation.getLatitude(),
                currentLocation.getLongitude(),
                radius,
                null,
                placeType,
                new PlacesService.NearbySearchCallback() {
                    @Override
                    public void onSuccess(NearbySearchResponse response) {
                        if (response.getResults() != null) {
                            loadingSpinner.setVisibility(View.GONE);
                            nextPageToken = response.getNextPageToken();
                            createSearchResultListItems(response.getResults());
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(NearbyPlacesActivity.this, "Failed", Toast.LENGTH_SHORT)
                                .show();
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
                            .setClickListener(() -> navigateToPlaceDetails(result))
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

        Toast.makeText(NearbyPlacesActivity.this, "Success", Toast.LENGTH_SHORT)
                .show();

        adapter.setListItems(listItems);
        adapter.notifyDataSetChanged();
    }

    private float getDistanceToTarget(SearchResult result) {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(result.getGeometry().getLocation().getLatitude());
        targetLocation.setLongitude(result.getGeometry().getLocation().getLongitude());
        return currentLocation.distanceTo(targetLocation) / 1000f;
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
            adapter.notifyDataSetChanged();
        }
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
                .setClickListener(() -> navigateToPlaceDetails(result))
                .build();
    }

    private void removeSpinnerListItem() {
        listItems.remove(listItems.size() - 1);
    }

    public void onLocationChanged(Location location) {
        Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();
        currentLocation = location;
        fetchExtendedPlaces();
    }
}
