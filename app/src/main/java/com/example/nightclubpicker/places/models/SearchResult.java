package com.example.nightclubpicker.places.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult implements Parcelable {
    private Geometry geometry;
    @SerializedName("icon")
    private String iconUrl;
    private String id;
    private String name;
    private List<Photo> photos;
    @SerializedName("permanently_closed")
    private boolean permanentlyClosed;
    @SerializedName("place_id")
    private String placeId;
    private double rating;
    private List<PlaceType> types;
    @SerializedName("user_ratings_total")
    private int userRatingsTotal;
    private String vicinity;

    protected SearchResult(Parcel in) {
        iconUrl = in.readString();
        id = in.readString();
        name = in.readString();
        permanentlyClosed = in.readByte() != 0;
        placeId = in.readString();
        rating = in.readDouble();
        userRatingsTotal = in.readInt();
        vicinity = in.readString();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public boolean isPermanentlyClosed() {
        return permanentlyClosed;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double getRating() {
        return rating;
    }

    public List<PlaceType> getTypes() {
        return types;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public String getVicinity() {
        return vicinity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconUrl);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (permanentlyClosed ? 1 : 0));
        dest.writeString(placeId);
        dest.writeDouble(rating);
        dest.writeInt(userRatingsTotal);
        dest.writeString(vicinity);
    }
}
