package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class SpinnerListItem implements ListItem {
    @Override
    public Type getType() {
        return Type.SPINNER_LIST_ITEM;
    }
}
