package com.example.nightclubpicker.common.view_holders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.list_items.ReviewListItem;
import com.example.nightclubpicker.common.picasso.RoundedRectTransform;
import com.example.nightclubpicker.common.views.StarRatingView;
import com.example.nightclubpicker.places.PlaceHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ReviewListItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView profilePictureView;
    private TextView nameView;
    private StarRatingView starRatingView;
    private TextView relativeTimeView;
    private TextView contentView;

    public ReviewListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        profilePictureView = itemView.findViewById(R.id.profilePicture);
        nameView = itemView.findViewById(R.id.name);
        starRatingView = itemView.findViewById(R.id.reviewStarRating);
        relativeTimeView = itemView.findViewById(R.id.relativeTime);
        contentView = itemView.findViewById(R.id.reviewContent);
    }

    public void setItems(ReviewListItem listItem) {
        Uri url = PlaceHelper.createUrlForPlaceImage(listItem.getProfilePictureUrl());

        Picasso.get()
                .load(url)
                .centerCrop()
                .transform(new RoundedRectTransform())
                .into(profilePictureView);

        nameView.setText(listItem.getName());
        starRatingView.setRating(listItem.getRating());
        relativeTimeView.setText(listItem.getRelativeTime());
        contentView.setText(listItem.getContent());
    }
}
