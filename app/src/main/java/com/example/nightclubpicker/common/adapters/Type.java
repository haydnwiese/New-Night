package com.example.nightclubpicker.common.adapters;

import androidx.annotation.LayoutRes;

import com.example.nightclubpicker.R;

public enum Type {
    HEADER_LIST_ITEM(R.layout.header_list_item),
    EXTRA_RESULT_LIST_ITEM(R.layout.result_list_item),
    TOP_RESULT_LIST_ITEM(R.layout.top_result_list_item),
    SUB_HEADER_LIST_ITEM(R.layout.sub_header_list_item),
    SPINNER_LIST_ITEM(R.layout.spinner_list_item),
    REVIEW_LIST_ITEM(R.layout.review_list_item),
    PLACE_ATTRIBUTE_LIST_ITEM(R.layout.place_attribute_list_item),
    OPEN_HOURS_LIST_ITEM(R.layout.open_hours_list_item);

    private int layout;

    Type(@LayoutRes int layout) {
        this.layout = layout;
    }

    public int getLayout() {
        return layout;
    }
}
