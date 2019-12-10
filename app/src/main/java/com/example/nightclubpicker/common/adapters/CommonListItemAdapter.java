package com.example.nightclubpicker.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.list_items.TopResultListItem;
import com.example.nightclubpicker.common.view_holders.HeaderListItemViewHolder;
import com.example.nightclubpicker.common.view_holders.ResultItemViewHolder;
import com.example.nightclubpicker.common.view_holders.SpinnerListItemViewHolder;

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
            case SPINNER_LIST_ITEM:
                return new SpinnerListItemViewHolder(inflate(parent, type.getLayout()));
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
            case SPINNER_LIST_ITEM:
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
