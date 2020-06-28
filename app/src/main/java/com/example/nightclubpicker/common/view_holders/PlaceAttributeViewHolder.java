package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.list_items.PlaceAttributeListItem;
import com.example.nightclubpicker.common.views.PlaceAttributeView;
import com.squareup.picasso.Picasso;

public class PlaceAttributeViewHolder extends RecyclerView.ViewHolder {
    private PlaceAttributeView placeAttributeView;
    private ImageView mapView;
    private LinearLayout divider1;

    public PlaceAttributeViewHolder(@NonNull View itemView) {
        super(itemView);

        placeAttributeView = (PlaceAttributeView) itemView.findViewById(R.id.attributeView);
        mapView = (ImageView) itemView.findViewById(R.id.staticMap);
        divider1 = (LinearLayout) itemView.findViewById(R.id.divider1);
    }

    public void setItems(PlaceAttributeListItem listItem) {
        placeAttributeView.setDescription(listItem.getLabel());
        placeAttributeView.setImageSrc(listItem.getIcon());

        if (listItem.getMapUrl() != null) {
            mapView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(listItem.getMapUrl())
                    .fit()
                    .into(mapView);
        } else {
            mapView.setVisibility(View.GONE);
        }

        divider1.setVisibility(listItem.isFirstItem() ? View.VISIBLE : View.INVISIBLE);

        itemView.setOnClickListener(v -> listItem.getClickListener().onItemClick());
    }
}
