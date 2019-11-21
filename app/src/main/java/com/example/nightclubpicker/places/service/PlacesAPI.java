package com.example.nightclubpicker.places.service;

import com.example.nightclubpicker.places.models.NearbySearchResponse;
import com.example.nightclubpicker.places.models.PlaceType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {
    String GET_NEARBY_SEARCH = "nearbysearch/json";

    @GET(GET_NEARBY_SEARCH)
    Call<NearbySearchResponse> fetchNearbyPlaces(@Query("key") String key,
                                                 @Query("location") String location,
                                                 @Query("radius") int radius,
                                                 @Query("keyword") String keyword,
                                                 @Query("type")PlaceType type);

    @GET(GET_NEARBY_SEARCH)
    Call<NearbySearchResponse> fetchAdditionalNearbyPlaces(@Query("key") String key,
                                                           @Query("pagetoken") String pageToken);
}
