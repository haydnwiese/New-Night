package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

import java.util.ArrayList;
import java.util.List;

public enum DressCode {
    casual(ResourceSingleton.getInstance().getString(R.string.casual)),
    semiFormal(ResourceSingleton.getInstance().getString(R.string.semi_formal)),
    formal(ResourceSingleton.getInstance().getString(R.string.formal));

    private String dressCode;

    DressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public String getDisplayString() {
        return dressCode;
    }

    public static List<String> getAllDisplayStrings() {
        List<String> displayStrings = new ArrayList<>();
        for (DressCode dressCode : DressCode.values()) {
            displayStrings.add(dressCode.getDisplayString());
        }
        return displayStrings;
    }
}
