package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getAllDisplayStrings() {
        List<String> displayStrings = new ArrayList<>();
        for (PlaceType placeType : PlaceType.values()) {
            displayStrings.add(placeType.getDisplayString());
        }
        return displayStrings;
    }
}
