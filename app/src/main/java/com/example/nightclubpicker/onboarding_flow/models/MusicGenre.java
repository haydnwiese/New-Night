package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

public enum MusicGenre {
    COUNTRY(ResourceSingleton.getInstance().getString(R.string.country)),
    DANCE(ResourceSingleton.getInstance().getString(R.string.dance)),
    HIP_HOP(ResourceSingleton.getInstance().getString(R.string.hip_hop)),
    ROCK(ResourceSingleton.getInstance().getString(R.string.rock));

    private String musicGenre;

    MusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public String getDisplayString() {
        return musicGenre;
    }
}