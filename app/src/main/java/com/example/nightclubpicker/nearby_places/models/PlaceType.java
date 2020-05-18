package com.example.nightclubpicker.nearby_places.models;

public enum PlaceType {
    night_club("Night Club"),
    bar("bar"),
    point_of_interest("Point of Interest"),
    establishment("Establishment");

    private String placeType;

    PlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceTypeString() {
        return placeType;
    }
}
