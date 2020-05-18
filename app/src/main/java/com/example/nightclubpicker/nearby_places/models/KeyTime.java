package com.example.nightclubpicker.nearby_places.models;

import java.io.Serializable;

public class KeyTime implements Serializable {
    private int day;
    private String time;

    public int getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
