package com.example.nightclubpicker.places.models;

import java.io.Serializable;

public class PlaceDetailsResponse implements Serializable {
    private DetailsResult result;

    public DetailsResult getResult() {
        return result;
    }
}
