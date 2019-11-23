package com.example.nightclubpicker.common.adapters;

import android.view.View;

import androidx.annotation.LayoutRes;

import com.example.nightclubpicker.R;

public enum Type {
    HEADER_LIST_ITEM(View.NO_ID),
    RESULT_LIST_ITEM(R.layout.results_view_list_item);

    private int layout;

    Type(@LayoutRes int layout) {
        this.layout = layout;
    }

    public int getLayout() {
        return layout;
    }
}
