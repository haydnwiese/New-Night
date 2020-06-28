package com.example.nightclubpicker.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.nightclubpicker.R;

public class PlaceAttributeView extends ConstraintLayout {
    private Drawable imageSrc;
    private String description;
    private View itemView;
    private ImageView iconView;
    private TextView descriptionView;

    public PlaceAttributeView(Context context) {
        super(context);
    }

    public PlaceAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        itemView = LayoutInflater.from(context).inflate(R.layout.place_attribute_layout, this);

        TypedArray attributeSet = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlaceAttributeView, 0, 0);

        if (attributeSet.hasValue(R.styleable.PlaceAttributeView_imageSrc)) {
            imageSrc = attributeSet.getDrawable(R.styleable.PlaceAttributeView_imageSrc);
        }

        if (attributeSet.hasValue(R.styleable.PlaceAttributeView_description)) {
            description = attributeSet.getString(R.styleable.PlaceAttributeView_description);
        }

        attributeSet.recycle();

        init();
    }

    public PlaceAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        iconView = itemView.findViewById(R.id.icon);
        descriptionView = itemView.findViewById(R.id.description);

        iconView.setImageDrawable(imageSrc);
        descriptionView.setText(description);
    }

    public Drawable getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Drawable imageSrc) {
        this.imageSrc = imageSrc;
        iconView.setImageDrawable(imageSrc);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        descriptionView.setText(description);
    }
}
