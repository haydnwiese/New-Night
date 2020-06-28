package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        // TODO: add calculation for dp and set on results screen
        if (!listItem.getHasLeftMargin()) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) headerTextView.getLayoutParams();
            params.setMarginStart(0);
            headerTextView.setLayoutParams(params);
        }
    }
}
