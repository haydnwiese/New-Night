package com.example.nightclubpicker.service;

import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;
import com.example.nightclubpicker.places.models.ExtendedPlace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExtendedPlacesAPI {
    String PLACES = "places";
    String GET_PLACES_FILTERED = "places-filtered";
    String PLACE_ID  = PLACES + "/{id}";

    @GET(PLACES)
    Call<List<ExtendedPlace>> fetchExtendedPlaces();

    @GET(GET_PLACES_FILTERED)
    Call<List<ExtendedPlace>> fetchFilteredPlaces(@Query("dress_code") DressCode dressCode,
                                                  @Query("music_genre") MusicGenre musicGenre,
                                                  @Query("size") VenueSize venueSize);

    @GET(PLACE_ID)
    Call<ExtendedPlace> fetchExtendedPlaceById(@Path("id") String googleId);

    @POST(PLACES)
    Call<ExtendedPlace> postExtendedPlace(@Body ExtendedPlace extendedPlace);

    @PUT(PLACES)
    Call<ExtendedPlace> putExtendedPlace(@Body ExtendedPlace extendedPlace);

    @DELETE(PLACE_ID)
    Call<Boolean> deleteExtendedPlace(@Path("id") String googleId);
}
