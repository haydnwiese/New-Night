package com.example.nightclubpicker.models;

import java.io.Serializable;

public class OpenPeriod implements Serializable {
    private KeyTime close;
    private KeyTime open;

    public KeyTime getClose() {
        return close;
    }

    public KeyTime getOpen() {
        return open;
    }
}
