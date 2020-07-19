package com.example.nightclubpicker.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.OpenHoursListItem;
import com.example.nightclubpicker.common.list_items.PlaceAttributeListItem;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.example.nightclubpicker.common.list_items.ReviewListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.list_items.TopResultListItem;
import com.example.nightclubpicker.common.view_holders.HeaderListItemViewHolder;
import com.example.nightclubpicker.common.view_holders.OpenHoursListItemViewHolder;
import com.example.nightclubpicker.common.view_holders.PlaceAttributeViewHolder;
import com.example.nightclubpicker.common.view_holders.ResultItemViewHolder;
import com.example.nightclubpicker.common.view_holders.ReviewListItemViewHolder;
import com.example.nightclubpicker.common.view_holders.SpinnerListItemViewHolder;
import com.example.nightclubpicker.common.views.PlaceAttributeView;

import java.util.List;

public class CommonListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ListItem> listItems;

    public CommonListItemAdapter(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Type type = Type.values()[viewType];
        switch(type) {
            case HEADER_LIST_ITEM:
            case SUB_HEADER_LIST_ITEM:
                return new HeaderListItemViewHolder(inflate(parent, type.getLayout()));
            case EXTRA_RESULT_LIST_ITEM:
            case TOP_RESULT_LIST_ITEM:
                return new ResultItemViewHolder(inflate(parent, type.getLayout()));
            case REVIEW_LIST_ITEM:
                return new ReviewListItemViewHolder(inflate(parent, type.getLayout()));
            case SPINNER_LIST_ITEM:
                return new SpinnerListItemViewHolder(inflate(parent, type.getLayout()));
            case PLACE_ATTRIBUTE_LIST_ITEM:
                return new PlaceAttributeViewHolder(inflate(parent, type.getLayout()));
            case OPEN_HOURS_LIST_ITEM:
                return new OpenHoursListItemViewHolder(inflate(parent, type.getLayout()));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        Type type = listItem.getType();

        switch(type) {
            case HEADER_LIST_ITEM:
                ((HeaderListItemViewHolder) holder).setItems((HeaderListItem) listItem);
                break;
            case SUB_HEADER_LIST_ITEM:
                ((HeaderListItemViewHolder) holder).setItems((SubHeaderListItem) listItem);
                break;
            case EXTRA_RESULT_LIST_ITEM:
                ((ResultItemViewHolder) holder).setItems((ResultListItem) listItem);
                break;
            case TOP_RESULT_LIST_ITEM:
                ((ResultItemViewHolder) holder).setItems((TopResultListItem) listItem);
                break;
            case REVIEW_LIST_ITEM:
                ((ReviewListItemViewHolder) holder).setItems((ReviewListItem) listItem);
                ReviewListItem reviewListItem = (ReviewListItem) listItem;
                holder.itemView.setOnClickListener(view -> {
                    reviewListItem.setExpanded(!reviewListItem.isExpanded());
                    notifyItemChanged(position);
                });
                break;
            case SPINNER_LIST_ITEM:
                break;
            case PLACE_ATTRIBUTE_LIST_ITEM:
                ((PlaceAttributeViewHolder) holder).setItems((PlaceAttributeListItem) listItem);
                break;
            case OPEN_HOURS_LIST_ITEM:
                ((OpenHoursListItemViewHolder) holder).setItems((OpenHoursListItem) listItem);
                OpenHoursListItem openHoursListItem = (OpenHoursListItem) listItem;
                holder.itemView.setOnClickListener(view -> {
                    openHoursListItem.setExpanded(!openHoursListItem.isExpanded());
                    notifyItemChanged(position);
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).getType().ordinal();
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    private View inflate(ViewGroup parent, @LayoutRes int layout) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(layout, parent, false);
    }
}
