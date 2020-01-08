package com.example.nightclubpicker.places.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailsResult implements Serializable {
    @SerializedName("formatted_address")
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;
    private Geometry geometry;
    private String name;
    @SerializedName("opening_hours")
    private OpeningHours openingHours;
    private List<Photo> photos;
    private double rating;
    private List<PlaceReview> reviews;
    @SerializedName("user_ratings_total")
    private int userRatingsTotal;
    @SerializedName("website")
    private String websiteUrl;
    @SerializedName("price_level")
    private int priceLevel;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public double getRating() {
        return rating;
    }

    public List<PlaceReview> getReviews() {
        return reviews;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public int getPriceLevel() {
        return priceLevel;
    }
}
