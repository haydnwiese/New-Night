package com.example.nightclubpicker.places.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private Geometry geometry;
    @SerializedName("icon")
    private String iconUrl;
    private String id;
    private String name;
    @SerializedName("permanently_closed")
    private boolean permanentlyClosed;
    @SerializedName("place_id")
    private String placeId;
    private double rating;
    private List<PlaceType> types;
    @SerializedName("user_ratings_total")
    private int userRatingsTotal;
    private String vicinity;

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPermanentlyClosed() {
        return permanentlyClosed;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double getRating() {
        return rating;
    }

    public List<PlaceType> getTypes() {
        return types;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public String getVicinity() {
        return vicinity;
    }
}
