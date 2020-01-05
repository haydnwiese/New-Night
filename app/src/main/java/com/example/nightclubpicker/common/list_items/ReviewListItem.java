package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class ReviewListItem implements ListItem {

    private String profilePictureUrl;
    private String name;
    private int rating;
    private String relativeTime;
    private String content;

    @Override
    public Type getType() {
        return Type.REVIEW_LIST_ITEM;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public String getContent() {
        return content;
    }

    public class Builder {
        private String profilePictureUrl;
        private String name;
        private int rating;
        private String relativeTime;
        private String content;

        public Builder setProfilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setRelativeTime(String relativeTime) {
            this.relativeTime = relativeTime;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public ReviewListItem build() {
            ReviewListItem listItem = new ReviewListItem();
            listItem.profilePictureUrl = profilePictureUrl;
            listItem.name = name;
            listItem.rating = rating;
            listItem.relativeTime = relativeTime;
            listItem.content = content;
            return listItem;
        }
    }
}
