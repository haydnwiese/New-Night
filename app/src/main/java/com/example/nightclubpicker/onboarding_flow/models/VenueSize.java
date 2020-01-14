package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

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
}
