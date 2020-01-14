package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

public enum PlaceType {
    NIGHT_CLUB(ResourceSingleton.getInstance().getString(R.string.night_club)),
    BAR(ResourceSingleton.getInstance().getString(R.string.bar));

    private String placeType;

    PlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getDisplayString() {
        return placeType;
    }
}
