package com.example.nightclubpicker.common.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.squareup.picasso.Picasso;

public class ResultItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView placeImageView;
    private TextView nameTextView;
    private View containerView;

    public ResultItemViewHolder(View itemView) {
        super(itemView);

        containerView = itemView;
        placeImageView = (ImageView) itemView.findViewById(R.id.resultImage);
        nameTextView = (TextView) itemView.findViewById(R.id.resultName);
    }

    public void setItems(ResultListItem listItem) {
        Picasso.get()
                .load(listItem.getImageUrl())
                .into(placeImageView);
        nameTextView.setText(listItem.getName());
        containerView.setOnClickListener(v -> listItem.getClickListener().onItemClick());
    }
}
