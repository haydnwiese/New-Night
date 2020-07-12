package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.OpenHoursListItem;
import com.example.nightclubpicker.common.views.PlaceAttributeView;

public class OpenHoursListItemViewHolder extends RecyclerView.ViewHolder {

    private PlaceAttributeView placeAttributeView;
    private TextView[] weekdayTextViews;
    private LinearLayout subItem;

    public OpenHoursListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        placeAttributeView = itemView.findViewById(R.id.attributeView);
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
    }

    public void setItems(OpenHoursListItem listItem) {
        placeAttributeView.setDescription(listItem.isOpen()
                ? ResourceSingleton.getInstance().getString(R.string.open)
                : ResourceSingleton.getInstance().getString(R.string.closed));
        for (int i = 0; i < weekdayTextViews.length; i++) {
            weekdayTextViews[i].setText(listItem.getWeekdayText()[i]);
        }
        subItem.setVisibility(listItem.isExpanded() ? View.VISIBLE : View.GONE);
    }
}
