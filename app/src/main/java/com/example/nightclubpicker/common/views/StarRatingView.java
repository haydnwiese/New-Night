package com.example.nightclubpicker.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.nightclubpicker.R;

public class StarRatingView extends RelativeLayout {
    private int rating;
    private ViewSize size;
    private View itemView;
    private ImageView[] stars = new ImageView[5];

    private enum ViewSize {
        SMALL,
        LARGE
    }

    public StarRatingView(Context context) {
        super(context);
    }

    public StarRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        itemView = LayoutInflater.from(context).inflate(R.layout.star_rating_layout, this);

        TypedArray setAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StarRatingView, 0, 0);

        if (setAttributes.hasValue(R.styleable.StarRatingView_rating)) {
            rating = setAttributes.getInt(R.styleable.StarRatingView_rating, 0);
        }

        if (setAttributes.hasValue(R.styleable.StarRatingView_size)) {
            size = ViewSize.values()[setAttributes.getInt(R.styleable.StarRatingView_size, 0)];
        }

        setAttributes.recycle();

        init();
    }

    public StarRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        stars[0] = itemView.findViewById(R.id.star1);
        stars[1] = itemView.findViewById(R.id.star2);
        stars[2] = itemView.findViewById(R.id.star3);
        stars[3] = itemView.findViewById(R.id.star4);
        stars[4] = itemView.findViewById(R.id.star5);

        updateStars();
    }

    private void updateStars() {
        for (int i = 0; i < rating; i++) {
            stars[i].setImageResource(R.drawable.ic_filled_in_star);
        }
    }

    public int getRating() {
        return rating;
    }

    public ViewSize getSize() {
        return size;
    }

    public void setRating(int rating) {
        this.rating = rating;
        updateStars();
    }

    public void setSize(ViewSize size) {
        this.size = size;
    }
}
