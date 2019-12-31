package com.example.nightclubpicker.places.place_details;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.places.PlaceHelper;
import com.example.nightclubpicker.places.models.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Photo> photoReferences;

    public ImageViewPagerAdapter(Context context, List<Photo> photoReferences) {
        this.context = context;
        this.photoReferences = photoReferences;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_image_layout, container, false);

        Uri url = PlaceHelper.createUrlForPlaceImage(photoReferences.get(position).getPhotoReference());
        ImageView imageView = view.findViewById(R.id.pagerImageView);
        Picasso.get()
                .load(url)
//                .centerCrop()
//                .resize(imageView.getLayoutParams().width, imageView.getLayoutParams().height)
                .into(imageView);
        imageView.setImageDrawable(ResourceSingleton.getInstance().getDrawable(R.drawable.ic_filled_in_star));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return photoReferences.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
