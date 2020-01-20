package com.example.nightclubpicker.onboarding_flow.models;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;

import java.util.ArrayList;
import java.util.List;

public enum MusicGenre {
    country(ResourceSingleton.getInstance().getString(R.string.country)),
    dance(ResourceSingleton.getInstance().getString(R.string.dance)),
    hipHop(ResourceSingleton.getInstance().getString(R.string.hip_hop)),
    rock(ResourceSingleton.getInstance().getString(R.string.rock));

    private String musicGenre;

    MusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public String getDisplayString() {
        return musicGenre;
    }

    public static List<String> getAllDisplayStrings() {
        List<String> displayStrings = new ArrayList<>();
        for (MusicGenre genre : MusicGenre.values()) {
            displayStrings.add(genre.getDisplayString());
        }
        return displayStrings;
    }
}
