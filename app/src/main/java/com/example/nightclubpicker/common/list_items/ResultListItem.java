package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public abstract class ResultListItem implements ListItem {
    String imageUrl;
    String name;
    String description;
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

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    @Override
    public abstract Type getType();
}
