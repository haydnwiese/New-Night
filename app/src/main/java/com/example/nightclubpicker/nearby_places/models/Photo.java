package com.example.nightclubpicker.nearby_places.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable {
    @SerializedName("photo_reference")
    private String photoReference;

    public String getPhotoReference() {
        return photoReference;
    }
}
