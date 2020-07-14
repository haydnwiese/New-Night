package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.OpenHoursListItem;
import com.example.nightclubpicker.common.views.PlaceAttributeView;

public class OpenHoursListItemViewHolder extends RecyclerView.ViewHolder {

    private TextView openStatusView;
    private TextView[] weekdayTextViews;
    private LinearLayout subItem;
    private ImageView expandArrow;

    public OpenHoursListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        openStatusView = itemView.findViewById(R.id.description);
        weekdayTextViews = new TextView[]{
                itemView.findViewById(R.id.monday),
                itemView.findViewById(R.id.tuesday),
                itemView.findViewById(R.id.wednesday),
                itemView.findViewById(R.id.thursday),
                itemView.findViewById(R.id.friday),
                itemView.findViewById(R.id.saturday),
                itemView.findViewById(R.id.sunday),
        };
        subItem = itemView.findViewById(R.id.subItem);
        expandArrow = itemView.findViewById(R.id.expandArrow);
    }

    public void setItems(OpenHoursListItem listItem) {
        openStatusView.setText(listItem.isOpen()
                ? ResourceSingleton.getInstance().getString(R.string.open)
                : ResourceSingleton.getInstance().getString(R.string.closed));
        for (int i = 0; i < weekdayTextViews.length; i++) {
            weekdayTextViews[i].setText(listItem.getWeekdayText()[i]);
        }
        subItem.setVisibility(listItem.isExpanded() ? View.VISIBLE : View.GONE);

        if (listItem.isExpanded()) {
            expandArrow.animate().setDuration(200).rotation(180);
        } else {
            expandArrow.animate().setDuration(200).rotation(0);
        }
    }
}
