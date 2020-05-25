package com.example.nightclubpicker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NearbySearchResponse implements Serializable {
    @SerializedName("next_page_token")
    private String nextPageToken;
    private List<SearchResult> results;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<SearchResult> getResults() {
        return results;
    }
}
