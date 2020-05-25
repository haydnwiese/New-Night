package com.example.nightclubpicker.nearby_places;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.models.PlaceType;
import com.example.nightclubpicker.models.SearchResult;
import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;
import com.example.nightclubpicker.place_details.PlaceDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyPlacesActivity extends BaseActivity implements NearbyPlacesContract.View {
    public static final String BUNDLE_KEY_SEARCH_RESULT = "bundleKeySearchResult";
    private static final String BUNDLE_KEY_RADIUS = "bundleKeyRadius";
    private static final String BUNDLE_KEY_DRESS_CODE = "bundleKeyDressCode";
    private static final String BUNDLE_KEY_MUSIC_GENRE = "bundleKeyMusicGenre";
    private static final String BUNDLE_KEY_PLACE_TYPE = "bundleKeyPlaceType";
    private static final String BUNDLE_KEY_VENUE_SIZE = "bundleKeyVenueSize";

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

    private NearbyPlacesContract.Presenter presenter;
    private CommonListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_list);
        setTitle(R.string.results);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonListItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        Intent filterData = getIntent();
        MusicGenre musicGenre = MusicGenre.values()[filterData.getIntExtra(BUNDLE_KEY_MUSIC_GENRE, 0)];
        VenueSize venueSize = VenueSize.values()[filterData.getIntExtra(BUNDLE_KEY_VENUE_SIZE, 0)];
        DressCode dressCode = DressCode.values()[filterData.getIntExtra(BUNDLE_KEY_DRESS_CODE, 0)];
        int radius = filterData.getIntExtra(BUNDLE_KEY_RADIUS, DEFAULT_SEARCH_RADIUS) * KM_TO_M_CONVERSION_FACTOR;
        PlaceType placeType = PlaceType.values()[filterData.getIntExtra(BUNDLE_KEY_PLACE_TYPE, 0)];

        presenter = new NearbyPlacesPresenter(this,
                (LocationManager) getSystemService(Context.LOCATION_SERVICE),
                musicGenre,
                venueSize,
                dressCode,
                radius,
                placeType);
        // TODO: Replace with better implementation strategy
        presenter.onViewCreated();

        initScrollListener();
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
                if (linearLayoutManager != null) {
                    presenter.updateResults(linearLayoutManager.findLastVisibleItemPosition());
                }
            }
        });
    }

    @Override
    public void updateListItems(List<ListItem> listItems) {
        adapter.setListItems(listItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyListInsertion(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void navigateToPlaceDetails(SearchResult result) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(BUNDLE_KEY_SEARCH_RESULT, result);
        startActivity(intent);
    }

    @Override
    public boolean hasLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setLoadingSpinnerVisibility(boolean isVisible) {
        loadingSpinner.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
