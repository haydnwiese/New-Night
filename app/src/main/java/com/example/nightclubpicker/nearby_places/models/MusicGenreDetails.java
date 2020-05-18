package com.example.nightclubpicker.nearby_places.models;

import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;

import java.io.Serializable;

public class MusicGenreDetails implements Serializable {
    private MusicGenre musicGenre;

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }
}
