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
            ResultListItem listItem = new TopResultListItem();
            listItem.imageUrl = imageUrl;
            listItem.name = name;
            listItem.description = description;
            listItem.clickListener = clickListener;
            return listItem;
        }
    }
}
