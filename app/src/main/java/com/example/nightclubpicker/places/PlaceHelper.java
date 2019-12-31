package com.example.nightclubpicker.places;

import android.net.Uri;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

public class PlaceHelper {
    private static final String HOST = "maps.googleapis.com";
    private static final String REQUEST_PATH = "maps/api/place/photo";
    private static final String QUERY_PARAM_HEIGHT = "maxheight";
    private static final String QUERY_PARAM_PHOTO_REFERENCE = "photoreference";
    private static final String QUERY_PARAM_KEY = "key";
    private static final String MAX_IMAGE_HEIGHT = "500";

    public static Uri createUrlForPlaceImage(String photoReference) {
        return new Uri.Builder()
                .scheme("https")
                .authority(HOST)
                .appendEncodedPath(REQUEST_PATH)
                .appendQueryParameter(QUERY_PARAM_KEY, ResourceSingleton.getInstance().getString(R.string.places_api_key))
                .appendQueryParameter(QUERY_PARAM_PHOTO_REFERENCE, photoReference)
                .appendQueryParameter(QUERY_PARAM_HEIGHT, MAX_IMAGE_HEIGHT)
                .build();
    }
}
