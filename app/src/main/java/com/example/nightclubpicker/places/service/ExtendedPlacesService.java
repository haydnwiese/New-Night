package com.example.nightclubpicker.places.service;

import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;
import com.example.nightclubpicker.places.models.ExtendedPlace;
import com.example.nightclubpicker.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExtendedPlacesService {
    private ExtendedPlacesAPI api = RetrofitInstance.getExtendedPlacesRetrofitInstance().create(ExtendedPlacesAPI.class);

    public interface ExtendedPlacesCallback {
        void onSuccess(List<ExtendedPlace> extendedPlacesList);

        void onSuccess(ExtendedPlace extendedPlace);

        void onFailure();
    }

    public interface SinglePlaceCallback {}

    public void fetchExtendedPlaces(ExtendedPlacesCallback callback) {
        Call<List<ExtendedPlace>> call = api.fetchExtendedPlaces();

        call.enqueue(new Callback<List<ExtendedPlace>>() {
            @Override
            public void onResponse(Call<List<ExtendedPlace>> call, Response<List<ExtendedPlace>> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<ExtendedPlace>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void fetchFilteredPlaces(DressCode dressCode,
                                    MusicGenre musicGenre,
                                    VenueSize venueSize,
                                    ExtendedPlacesCallback callback) {
        Call<List<ExtendedPlace>> call = api.fetchFilteredPlaces(dressCode, musicGenre, venueSize);

        call.enqueue(new Callback<List<ExtendedPlace>>() {
            @Override
            public void onResponse(Call<List<ExtendedPlace>> call, Response<List<ExtendedPlace>> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<ExtendedPlace>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void fetchExtendedPlaceById(String googleId, ExtendedPlacesCallback callback) {
        Call<ExtendedPlace> call = api.fetchExtendedPlaceById(googleId);

        call.enqueue(new Callback<ExtendedPlace>() {
            @Override
            public void onResponse(Call<ExtendedPlace> call, Response<ExtendedPlace> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ExtendedPlace> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}