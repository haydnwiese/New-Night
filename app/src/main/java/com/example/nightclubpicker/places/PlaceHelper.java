package com.example.nightclubpicker.places;

import android.net.Uri;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

public class PlaceHelper {
    private static final String HOST = "maps.googleapis.com";
    private static final String PLACE_PHOTO_REQUEST_PATH = "maps/api/place/photo";
    private static final String PLACE_PHOTO_QUERY_PARAM_HEIGHT = "maxheight";
    private static final String PLACE_PHOTO_QUERY_PARAM_PHOTO_REFERENCE = "photoreference";
    private static final String PLACE_PHOTO_QUERY_PARAM_KEY = "key";
    private static final String PLACE_PHOTO_MAX_IMAGE_HEIGHT = "500";

    private static final String STATIC_MAP_REQUEST_PATH = "maps/api/staticmap";
    private static final String STATIC_MAP_QUERY_PARAM_KEY = "key";
    private static final String STATIC_MAP_QUERY_PARAM_ZOOM = "zoom";
    private static final String STATIC_MAP_QUERY_PARAM_SIZE = "size";
    private static final String STATIC_MAP_QUERY_PARAM_MARKERS = "markers";

    private static final String STATIC_MAP_ZOOM_LEVEL = "14";
    private static final String STATIC_MAP_SIZE = "600x250";

    public static Uri createUrlForPlaceImage(String photoReference) {
        return new Uri.Builder()
                .scheme("https")
                .authority(HOST)
                .appendEncodedPath(PLACE_PHOTO_REQUEST_PATH)
                .appendQueryParameter(PLACE_PHOTO_QUERY_PARAM_KEY, ResourceSingleton.getInstance().getString(R.string.places_api_key))
                .appendQueryParameter(PLACE_PHOTO_QUERY_PARAM_PHOTO_REFERENCE, photoReference)
                .appendQueryParameter(PLACE_PHOTO_QUERY_PARAM_HEIGHT, PLACE_PHOTO_MAX_IMAGE_HEIGHT)
                .build();
    }

    public static Uri createUrlForStaticMap(double latitude, double longitude) {
        return new Uri.Builder()
                .scheme("https")
                .authority(HOST)
                .appendEncodedPath(STATIC_MAP_REQUEST_PATH)
                .appendQueryParameter(STATIC_MAP_QUERY_PARAM_KEY, ResourceSingleton.getInstance().getString(R.string.places_api_key))
                .appendQueryParameter(STATIC_MAP_QUERY_PARAM_ZOOM, STATIC_MAP_ZOOM_LEVEL)
                .appendQueryParameter(STATIC_MAP_QUERY_PARAM_SIZE, STATIC_MAP_SIZE)
                .appendQueryParameter(STATIC_MAP_QUERY_PARAM_MARKERS, ResourceSingleton.getInstance().getString(R.string.markers_parameter, "red", latitude, longitude))
                .build();
    }

    public static String generatePriceLevelString(int priceLevel) {
        switch(priceLevel) {
            case 1:
                return "$";
            case 2:
                return "$$";
            case 3:
                return "$$$";
            case 4:
                return "$$$$";
            default:
                return null;
        }
    }

    public static String getDistanceDisplay(float distance) {
        String distanceAsString;
        if (distance > 1) {
            distanceAsString = String.valueOf(Math.round(distance));
        } else {
            distanceAsString = "<1";
        }
        return ResourceSingleton.getInstance().getString(R.string.distance_from_user, distanceAsString);
    }
}
