package com.example.nightclubpicker.common.view_holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.list_items.ResultListItem;

public class ResultItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView placeImageView;
    private TextView nameTextView;

    public ResultItemViewHolder(View itemView) {
        super(itemView);

        placeImageView = (ImageView) itemView.findViewById(R.id.resultImage);
        nameTextView = (TextView) itemView.findViewById(R.id.resultName);
    }

    public void setItems(ResultListItem listItem) {
        placeImageView.setImageResource(listItem.getImageId());
        nameTextView.setText(listItem.getName());
    }
}
