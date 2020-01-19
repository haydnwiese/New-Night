package com.example.nightclubpicker.places.service;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.places.models.DetailsResult;
import com.example.nightclubpicker.places.models.NearbySearchResponse;
import com.example.nightclubpicker.places.models.PlaceDetailsResponse;
import com.example.nightclubpicker.places.models.PlaceType;
import com.example.nightclubpicker.places.models.SearchResult;
import com.example.nightclubpicker.retrofit.RetrofitInstance;

import java.util.List;

import com.example.nightclubpicker.common.ResourceSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesService {
    private final String API_KEY = ResourceSingleton.getInstance().getString(R.string.places_api_key);

    private PlacesAPI api = RetrofitInstance.getPlacesRetrofitInstance().create(PlacesAPI.class);

    public interface NearbySearchCallback {
        void onSuccess(NearbySearchResponse response);

        void onFailure();
    }

    public interface PlaceDetailsCallback {
        void onSuccess(DetailsResult response);

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
                if (response.body() != null) {
                    callback.onSuccess(response.body());
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
                if (response.body() != null) {
                    callback.onSuccess(response.body());
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

    public void fetchPlaceDetails(String placeId, PlaceDetailsCallback callback) {
        StringBuilder fields = new StringBuilder();
        fields.append("name,")
                .append("rating,")
                .append("formatted_phone_number,")
                .append("geometry,")
                .append("formatted_address,")
                .append("photo,")
                .append("price_level,")
                .append("review,")
                .append("user_ratings_total,")
                .append("website,")
                .append("opening_hours");

        Call<PlaceDetailsResponse> call = api.fetchPlaceDetails(API_KEY, placeId, fields.toString());
        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                if (response.body() != null
                        && response.body().getResult() != null) {
                    callback.onSuccess(response.body().getResult());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
