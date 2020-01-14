package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

public enum DressCode {
    CASUAL(ResourceSingleton.getInstance().getString(R.string.casual)),
    SEMI_FORMAL(ResourceSingleton.getInstance().getString(R.string.semi_formal)),
    FORMAL(ResourceSingleton.getInstance().getString(R.string.formal));

    private String dressCode;

    DressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public String getDisplayString() {
        return dressCode;
    }
}
