package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class ResultListItem implements ListItem {
    // TODO: Change to URL string
    private int imageId;
    private String name;
    private String description;
    private

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private int imageId;
        private String name;
        private String description;

        public Builder setImageId(int imageId) {
            this.imageId = imageId;
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

        public ResultListItem build() {
            ResultListItem listItem = new ResultListItem();
            listItem.imageId = imageId;
            listItem.name = name;
            listItem.description = description;
            return listItem;
        }
    }

    @Override
    public Type getType() {
        return Type.RESULT_LIST_ITEM;
    }
}
