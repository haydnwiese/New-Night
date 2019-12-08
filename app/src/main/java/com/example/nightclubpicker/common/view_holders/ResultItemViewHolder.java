package com.example.nightclubpicker.common.view_holders;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.picasso.RoundedRectTransform;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.ResultListItem;
import com.squareup.picasso.Picasso;

public class ResultItemViewHolder extends RecyclerView.ViewHolder {
    private static final String HOST = "maps.googleapis.com";
    private static final String REQUEST_PATH = "maps/api/place/photo";
    private static final String QUERY_PARAM_HEIGHT = "maxheight";
    private static final String QUERY_PARAM_PHOTO_REFERENCE = "photoreference";
    private static final String QUERY_PARAM_KEY = "key";
    private static final String MAX_IMAGE_HEIGHT = "500";

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
        Uri url = new Uri.Builder()
                .scheme("https")
                .authority(HOST)
                .appendEncodedPath(REQUEST_PATH)
                .appendQueryParameter(QUERY_PARAM_KEY, ResourceSingleton.getInstance().getString(R.string.places_api_key))
                .appendQueryParameter(QUERY_PARAM_PHOTO_REFERENCE, listItem.getImageUrl())
                .appendQueryParameter(QUERY_PARAM_HEIGHT, MAX_IMAGE_HEIGHT)
                .build();

        Picasso.get()
                .load(url)
                .centerCrop()
                .resize(placeImageView.getLayoutParams().width - 5, placeImageView.getLayoutParams().height)
                .transform(new RoundedRectTransform())
                .into(placeImageView);

        nameTextView.setText(listItem.getName());
        containerView.setOnClickListener(v -> listItem.getClickListener().onItemClick());
    }


}
