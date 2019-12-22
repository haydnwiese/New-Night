package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class TopResultListItem extends ResultListItem {
    @Override
    public Type getType() {
        return Type.TOP_RESULT_LIST_ITEM;
    }

    public static class Builder {
        private String imageUrl;
        private String name;
        private String description;
        private double rating;
        private int reviewCount;
        private float distance;
        private int priceLevel;
        private OnItemClickListener clickListener;

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public Builder setDistance(float distance) {
            this.distance = distance;
            return this;
        }

        public Builder setPriceLevel(int priceLevel) {
            this.priceLevel = priceLevel;
            return this;
        }

        public Builder setClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public ResultListItem build() {
            ResultListItem listItem = new TopResultListItem();
            listItem.imageUrl = imageUrl;
            listItem.name = name;
            listItem.description = description;
            listItem.rating = rating;
            listItem.reviewCount = reviewCount;
            listItem.distance = distance;
            listItem.priceLevel = priceLevel;
            listItem.clickListener = clickListener;
            return listItem;
        }
    }
}
