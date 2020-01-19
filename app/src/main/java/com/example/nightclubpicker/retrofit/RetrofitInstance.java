package com.example.nightclubpicker.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit placesRetrofit;
    private static Retrofit extendedPlacesRetrofit;
    private static final String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String EXTENDED_PLACES_BASE_URL = "localhost:8080";

    public static Retrofit getPlacesRetrofitInstance() {
        if (placesRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            placesRetrofit = new Retrofit.Builder()
                    .baseUrl(PLACES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return placesRetrofit;
    }

    public static Retrofit getExtendedPlacesRetrofitInstance() {
        if (placesRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            placesRetrofit = new Retrofit.Builder()
                    .baseUrl(EXTENDED_PLACES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return placesRetrofit;
    }
}
