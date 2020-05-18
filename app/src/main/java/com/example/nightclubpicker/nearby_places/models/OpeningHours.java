package com.example.nightclubpicker.nearby_places.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OpeningHours implements Serializable {
    @SerializedName("open_now")
    private boolean openNow;
    private List<OpenPeriod> periods;
    @SerializedName("weekday_text")
    private List<String> weekdayText;

    public boolean isOpenNow() {
        return openNow;
    }

    public List<OpenPeriod> getPeriods() {
        return periods;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }
}
