package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

import java.util.ArrayList;
import java.util.List;

public enum VenueSize {
    small(ResourceSingleton.getInstance().getString(R.string.small), ResourceSingleton.getInstance().getString(R.string.small_venue)),
    medium(ResourceSingleton.getInstance().getString(R.string.medium), ResourceSingleton.getInstance().getString(R.string.medium_venue)),
    large(ResourceSingleton.getInstance().getString(R.string.large), ResourceSingleton.getInstance().getString(R.string.large_venue));

    private String venueSize;
    private String detailsDisplay;

    VenueSize(String venueSize, String detailsDisplay) {
        this.venueSize = venueSize;
        this.detailsDisplay = detailsDisplay;
    }

    public String getDisplayString() {
        return venueSize;
    }

    public String getDetailsDisplayString() {
        return detailsDisplay;
    }

    public static List<String> getAllDisplayStrings() {
        List<String> displayStrings = new ArrayList<>();
        for (VenueSize size : VenueSize.values()) {
            displayStrings.add(size.getDisplayString());
        }
        return displayStrings;
    }
}
