package com.example.nightclubpicker.common.list_items;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.nightclubpicker.common.adapters.Type;

public class PlaceAttributeListItem implements ListItem {

    private String label;
    private Drawable icon;
    private Uri mapUrl;
    private boolean isFirstItem = false;

    public String getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Uri getMapUrl() {
        return mapUrl;
    }

    public boolean isFirstItem() {
        return isFirstItem;
    }

    @Override
    public Type getType() {
        return Type.PLACE_ATTRIBUTE_LIST_ITEM;
    }

    public static class Builder {
        private String label;
        private Drawable icon;
        private Uri mapUrl;
        private boolean isFirstItem;

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setMapUrl(Uri mapUrl) {
            this.mapUrl = mapUrl;
            return this;
        }

        public Builder setFirstItem(boolean isFirstItem) {
            this.isFirstItem = isFirstItem;
            return this;
        }

        public PlaceAttributeListItem build() {
            PlaceAttributeListItem listItem = new PlaceAttributeListItem();
            listItem.label = label;
            listItem.icon = icon;
            listItem.mapUrl = mapUrl;
            listItem.isFirstItem = isFirstItem;
            return listItem;
        }
    }
}
