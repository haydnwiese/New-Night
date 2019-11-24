package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class HeaderListItem implements ListItem{
    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public Type getType() {
        return Type.HEADER_LIST_ITEM;
    }

    public static class Builder {
        private String title;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public HeaderListItem build() {
            HeaderListItem item = new HeaderListItem();
            item.title = title;
            return item;
        }
    }
}
