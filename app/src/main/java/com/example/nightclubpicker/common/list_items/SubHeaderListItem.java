package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class SubHeaderListItem implements ListItem {
    private String subHeader;

    public String getSubHeader() {
        return subHeader;
    }

    @Override
    public Type getType() {
        return Type.SUB_HEADER_LIST_ITEM;
    }

    public static class Builder {
        private String subHeader;

        public Builder setSubHeader(String subHeader) {
            this.subHeader = subHeader;
            return this;
        }

        public SubHeaderListItem build() {
            SubHeaderListItem listItem = new SubHeaderListItem();
            listItem.subHeader = this.subHeader;
            return listItem;
        }
    }
}
