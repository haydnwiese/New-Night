package com.example.nightclubpicker.models;

import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;

import java.io.Serializable;
import java.util.List;

public class ExtendedPlace implements Serializable {
    private String googleId;
    private String name;
    private VenueSize size;
    private DressCode dressCode;
    private List<MusicGenreDetails> musicGenres;

    public String getGoogleId() {
        return googleId;
    }

    public String getName() {
        return name;
    }

    public VenueSize getSize() {
        return size;
    }

    public DressCode getDressCode() {
        return dressCode;
    }

    public List<MusicGenreDetails> getMusicGenres() {
        return musicGenres;
    }
}
