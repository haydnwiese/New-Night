package com.example.nightclubpicker.nearby_places.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceReview implements Serializable {
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_url")
    private String authorUrl;
    private String language;
    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;
    private int rating;
    @SerializedName("relative_time_description")
    private String relativeTimeDescription;
    private String text;
    private int time;

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public int getRating() {
        return rating;
    }

    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }

    public String getText() {
        return text;
    }

    public int getTime() {
        return time;
    }
}
