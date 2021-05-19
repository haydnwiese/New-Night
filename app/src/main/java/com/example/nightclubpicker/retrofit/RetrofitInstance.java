package com.example.nightclubpicker.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit placesRetrofit;
    private static Retrofit extendedPlacesRetrofit;
    private static final String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/";
//    private static final String EXTENDED_PLACES_BASE_URL = "http://10.0.2.2:8080/";
    private static final String EXTENDED_PLACES_BASE_URL = "http://192.168.1.11:8080/";

    public static Retrofit getPlacesRetrofitInstance() {
        if (placesRetrofit == null) {
            placesRetrofit = RetrofitBuilder.createRetrofitInstance(PLACES_BASE_URL);
        }
        return placesRetrofit;
    }

    public static Retrofit getExtendedPlacesRetrofitInstance() {
        if (extendedPlacesRetrofit == null) {
            extendedPlacesRetrofit = RetrofitBuilder.createRetrofitInstance(EXTENDED_PLACES_BASE_URL);
        }
        return extendedPlacesRetrofit;
    }
}
