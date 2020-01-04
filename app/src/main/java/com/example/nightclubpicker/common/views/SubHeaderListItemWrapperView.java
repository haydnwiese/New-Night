package com.example.nightclubpicker.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.nightclubpicker.common.adapters.Type;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.view_holders.HeaderListItemViewHolder;

public class SubHeaderListItemWrapperView extends RelativeLayout {
    HeaderListItemViewHolder viewHolder;

    public SubHeaderListItemWrapperView(Context context) {
        super(context);

        View itemView = LayoutInflater.from(context).inflate(Type.SUB_HEADER_LIST_ITEM.getLayout(), this);
        viewHolder = new HeaderListItemViewHolder(itemView);
    }

    public SubHeaderListItemWrapperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View itemView = LayoutInflater.from(context).inflate(Type.SUB_HEADER_LIST_ITEM.getLayout(), this);
        viewHolder = new HeaderListItemViewHolder(itemView);
    }

    public SubHeaderListItemWrapperView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setItems(SubHeaderListItem listItem) {
        viewHolder.setItems(listItem);
    }
}
