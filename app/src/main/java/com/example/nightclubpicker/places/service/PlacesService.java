package com.example.nightclubpicker.places.service;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.places.models.NearbySearchResponse;
import com.example.nightclubpicker.places.models.PlaceType;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.retrofit.RetrofitInstance;

import java.util.List;

import com.example.nightclubpicker.ResourceSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesService {
    private final String API_KEY = ResourceSingleton.getResourcesInstance().getString(R.string.places_api_key);

    private PlacesAPI api = RetrofitInstance.getRetrofitInstance().create(PlacesAPI.class);

    public interface NearbySearchCallback {
        void onSuccess(List<SearchResult> searchResults);

        void onFailure();
    }

    public void fetchNearbyPlaces(double latitude,
                                  double longitude,
                                  int radius,
                                  String keyword,
                                  PlaceType type,
                                  final NearbySearchCallback callback) {
        Call<NearbySearchResponse> call = api.fetchNearbyPlaces(API_KEY,
                String.format("%f,%f", latitude, longitude),
                radius,
                keyword,
                type);

        call.enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void fetchAdditionalNearbyPlaces(String pageToken, final NearbySearchCallback callback) {
        Call<NearbySearchResponse> call = api.fetchAdditionalNearbyPlaces(API_KEY, pageToken);
        call.enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
