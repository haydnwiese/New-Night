package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

import java.util.ArrayList;
import java.util.List;

public enum VenueSize {
    SMALL(ResourceSingleton.getInstance().getString(R.string.small)),
    MEDIUM(ResourceSingleton.getInstance().getString(R.string.medium)),
    LARGE(ResourceSingleton.getInstance().getString(R.string.large));

    private String venueSize;

    VenueSize(String venueSize) {
        this.venueSize = venueSize;
    }

    public String getDisplayString() {
        return venueSize;
    }

    public static List<String> getAllDisplayStrings() {
        List<String> displayStrings = new ArrayList<>();
        for (VenueSize size : VenueSize.values()) {
            displayStrings.add(size.getDisplayString());
        }
        return displayStrings;
    }
}
