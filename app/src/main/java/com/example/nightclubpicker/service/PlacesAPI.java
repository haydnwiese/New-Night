package com.example.nightclubpicker.service;

import com.example.nightclubpicker.places.models.NearbySearchResponse;
import com.example.nightclubpicker.places.models.PlaceDetailsResponse;
import com.example.nightclubpicker.places.models.PlaceType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {
    String GET_NEARBY_SEARCH = "nearbysearch/json";
    String GET_DETAILS = "details/json";

    @GET(GET_NEARBY_SEARCH)
    Call<NearbySearchResponse> fetchNearbyPlaces(@Query("key") String key,
                                                 @Query("location") String location,
                                                 @Query("radius") int radius,
                                                 @Query("keyword") String keyword,
                                                 @Query("type")PlaceType type);

    @GET(GET_NEARBY_SEARCH)
    Call<NearbySearchResponse> fetchAdditionalNearbyPlaces(@Query("key") String key,
                                                           @Query("pagetoken") String pageToken);

    @GET(GET_DETAILS)
    Call<PlaceDetailsResponse> fetchPlaceDetails(@Query("key") String key,
                                                 @Query("place_id") String placeId,
                                                 @Query("fields") String fields);
}
