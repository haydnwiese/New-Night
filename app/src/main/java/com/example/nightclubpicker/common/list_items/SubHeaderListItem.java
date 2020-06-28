package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class SubHeaderListItem implements ListItem {
    private String subHeader;
    private boolean hasLeftMargin = true;

    public SubHeaderListItem() {}

    public SubHeaderListItem(String subHeader) {
        this.subHeader = subHeader;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public boolean getHasLeftMargin() {
        return hasLeftMargin;
    }

    @Override
    public Type getType() {
        return Type.SUB_HEADER_LIST_ITEM;
    }

    public static class Builder {
        private String subHeader;
        private boolean hasLeftMargin = true;

        public Builder setSubHeader(String subHeader) {
            this.subHeader = subHeader;
            return this;
        }

        public Builder setHasLeftMargin(boolean hasLeftMargin) {
            this.hasLeftMargin = hasLeftMargin;
            return this;
        }

        public SubHeaderListItem build() {
            SubHeaderListItem listItem = new SubHeaderListItem();
            listItem.subHeader = this.subHeader;
            listItem.hasLeftMargin = this.hasLeftMargin;
            return listItem;
        }
    }
}
