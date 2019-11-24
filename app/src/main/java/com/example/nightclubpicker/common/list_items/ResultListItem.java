package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class ResultListItem implements ListItem {
    private String imageUrl;
    private String name;
    private String description;
    private OnItemClickListener clickListener;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    public static class Builder {
        private String imageUrl;
        private String name;
        private String description;
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

        public Builder setClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public ResultListItem build() {
            ResultListItem listItem = new ResultListItem();
            listItem.imageUrl = imageUrl;
            listItem.name = name;
            listItem.description = description;
            listItem.clickListener = clickListener;
            return listItem;
        }
    }

    @Override
    public Type getType() {
        return Type.RESULT_LIST_ITEM;
    }
}
