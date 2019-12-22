package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public abstract class ResultListItem implements ListItem {
    String imageUrl;
    String name;
    String description;
    double rating;
    int reviewCount;
    float distance;
    int priceLevel;
    OnItemClickListener clickListener;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public float getDistance() {
        return distance;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    @Override
    public abstract Type getType();
}
