package com.example.nightclubpicker.common.view_holders;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.example.nightclubpicker.common.views.StarRatingView;
import com.example.nightclubpicker.places.PlaceHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ResultItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView placeImageView;
    private TextView nameTextView;
    private ProgressBar loadingSpinner;
    private StarRatingView starRatingView;
    private TextView exactRatingView;
    private TextView reviewCountView;
    private TextView distanceView;
    private TextView priceIndicatorView;
    private TextView dotSeparator;

    public ResultItemViewHolder(View itemView) {
        super(itemView);

        placeImageView = (ImageView) itemView.findViewById(R.id.resultImage);
        nameTextView = (TextView) itemView.findViewById(R.id.resultName);
        loadingSpinner = (ProgressBar) itemView.findViewById(R.id.loadingSpinner);
        starRatingView = (StarRatingView) itemView.findViewById(R.id.starRatingView);
        exactRatingView = (TextView) itemView.findViewById(R.id.exactStarRating);
        reviewCountView = (TextView) itemView.findViewById(R.id.numberOfReviews);
        distanceView = (TextView) itemView.findViewById(R.id.distance);
        priceIndicatorView = (TextView) itemView.findViewById(R.id.priceIndicator);
        dotSeparator = (TextView) itemView.findViewById(R.id.dot);
    }

    @SuppressLint("SetTextI18n")
    public void setItems(ResultListItem listItem) {
        Uri url = PlaceHelper.createUrlForPlaceImage(listItem.getImageUrl());

        loadingSpinner.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(url)
                .centerCrop()
                .resize(placeImageView.getLayoutParams().width - 5, placeImageView.getLayoutParams().height)
                .into(placeImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        loadingSpinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        starRatingView.setRating(listItem.getRating());
        nameTextView.setText(listItem.getName());
        exactRatingView.setText(Double.toString(listItem.getRating()));
        reviewCountView.setText(ResourceSingleton.getInstance().getString(R.string.review_count, listItem.getReviewCount()));
        distanceView.setText(getDistanceDisplay(listItem.getDistance()));
        priceIndicatorView.setText(generatePriceLevelString(listItem.getPriceLevel()));
        dotSeparator.setVisibility(listItem.getPriceLevel() == 0 ? View.GONE : View.VISIBLE);
        itemView.setOnClickListener(v -> listItem.getClickListener().onItemClick());
    }

    private String getDistanceDisplay(float distance) {
        String distanceAsString;
        if (distance > 1) {
            distanceAsString = String.valueOf(Math.round(distance));
        } else {
            distanceAsString = "<1";
        }
        return ResourceSingleton.getInstance().getString(R.string.distance_from_user, distanceAsString);
    }

    private String generatePriceLevelString(int priceLevel) {
        switch(priceLevel) {
            case 1:
                return "$";
            case 2:
                return "$$";
            case 3:
                return "$$$";
            case 4:
                return "$$$$";
            default:
                return null;
        }
    }
}
