package com.example.nightclubpicker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geometry implements Serializable{
    private Location location;

    public Location getLocation() {
        return location;
    }

    public class Location implements Serializable {
        @SerializedName("lat")
        private double latitude;
        @SerializedName("lng")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
