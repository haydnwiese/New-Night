package com.example.nightclubpicker.places;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.adapters.DividerItemDecoration;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.example.nightclubpicker.places.models.PlaceType;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.places.place_details.PlaceDetailsActivity;
import com.example.nightclubpicker.places.service.PlacesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nightclubpicker.MainActivity.BUNDLE_LAT;
import static com.example.nightclubpicker.MainActivity.BUNDLE_LNG;

public class NearbyPlacesListActivity extends BaseActivity {

    @BindView(R.id.resultsRecyclerView)
    RecyclerView recyclerView;

    CommonListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_list);
        setTitle(R.string.results);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(ResourceSingleton.getInstance().getDrawable(R.drawable.divider)));
        adapter = new CommonListItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        fetchPlaces(intent.getDoubleExtra(BUNDLE_LAT, 0), intent.getDoubleExtra(BUNDLE_LNG, 0));
    }

    private void navigateToPlaceDetails(SearchResult result) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        startActivity(intent);
    }

    private void fetchPlaces(double lat, double lng) {
        PlacesService placesService = new PlacesService();
        placesService.fetchNearbyPlaces(lat,
                lng,
                50000,
                null,
                PlaceType.night_club,
                new PlacesService.NearbySearchCallback() {
                    @Override
                    public void onSuccess(List<SearchResult> searchResults) {
                        List<ListItem> listItems = new ArrayList<>();

                        listItems.add(new HeaderListItem.Builder()
                                .setTitle(ResourceSingleton.getInstance().getString(R.string.results))
                                .build());

                        for (SearchResult result : searchResults) {
                            listItems.add(new ResultListItem.Builder()
                                    .setImageUrl(result.getPhotos().get(0).getPhotoReference())
                                    .setName(result.getName())
                                    .setClickListener(() -> navigateToPlaceDetails(result))
                                    .build());
                        }

                        Toast.makeText(NearbyPlacesListActivity.this, "Success", Toast.LENGTH_SHORT)
                                .show();

                        adapter.setListItems(listItems);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(NearbyPlacesListActivity.this, "Failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
