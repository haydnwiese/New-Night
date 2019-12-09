package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;

public class HeaderListItemViewHolder extends RecyclerView.ViewHolder {
    private TextView headerTextView;

    public HeaderListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        headerTextView = (TextView) itemView.findViewById(R.id.headerTextView);
    }

    public void setItems(HeaderListItem listItem) {
        headerTextView.setText(listItem.getTitle());
    }

    public void setItems(SubHeaderListItem listItem) {
        headerTextView.setText(listItem.getSubHeader());
    }
}
