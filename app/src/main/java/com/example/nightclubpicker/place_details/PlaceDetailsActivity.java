package com.example.nightclubpicker.place_details;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.adapters.CommonListItemAdapter;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.views.HeaderListItemWrapperView;
import com.example.nightclubpicker.common.views.PlaceAttributeView;
import com.example.nightclubpicker.common.views.StarRatingView;
import com.example.nightclubpicker.common.views.SubHeaderListItemWrapperView;
import com.example.nightclubpicker.models.Photo;
import com.example.nightclubpicker.models.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.nightclubpicker.nearby_places.NearbyPlacesActivity.BUNDLE_KEY_SEARCH_RESULT;

public class PlaceDetailsActivity extends BaseActivity implements PlaceDetailsContract.View {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.dotsView)
    LinearLayout dotsLayout;
    @BindView(R.id.header)
    HeaderListItemWrapperView headerWrapperView;
    @BindView(R.id.starRatingView)
    StarRatingView starRatingView;
    @BindView(R.id.exactStarRating)
    TextView exactRatingView;
    @BindView(R.id.numberOfReviews)
    TextView reviewCountView;
    @BindView(R.id.dot)
    TextView dotSeparatorView;
    @BindView(R.id.priceIndicator)
    TextView priceLevelView;
    @BindView(R.id.venueSize)
    TextView venueSizeView;
    @BindView(R.id.dressCode)
    TextView dressCodeView;
    @BindView(R.id.recyclerView)
    RecyclerView reviewsRecyclerView;

    PlaceDetailsContract.Presenter presenter;

    ImageViewPagerAdapter viewPagerAdapter;
    CommonListItemAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        setTitle(R.string.details);
        ButterKnife.bind(this);

        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new CommonListItemAdapter(new ArrayList<>());
        reviewsRecyclerView.setAdapter(recyclerViewAdapter);

        Intent intent = getIntent();
        if (intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT) != null) {
            SearchResult searchResult = intent.getParcelableExtra(BUNDLE_KEY_SEARCH_RESULT);
            presenter = new PlaceDetailsPresenter(this, searchResult);
            presenter.onViewCreated();
        }
    }

    @Override
    public void initViewPager(List<Photo> photos) {
        if (photos != null) {
            photos = photos.subList(0, Math.min(photos.size(), 4));
            viewPagerAdapter = new ImageViewPagerAdapter(PlaceDetailsActivity.this, photos);
            viewPager.setAdapter(viewPagerAdapter);
            addViewPagerDots(viewPagerAdapter.getCount(), 0);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    addViewPagerDots(viewPagerAdapter.getCount(), position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            viewPager.setVisibility(View.GONE);
        }
    }

    private void addViewPagerDots(int size, int current) {
        if (size <= 1) {
            return;
        }

        ImageView[] dots  = new ImageView[size];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.indicator_dot_dark);
            dotsLayout.addView(dots[i]);
        }
        dots[current].setImageResource(R.drawable.indicator_dot_light);
    }

    @Override
    public void updateListItems(List<ListItem> items) {
        recyclerViewAdapter.setListItems(items);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void setHeaderWrapperView(HeaderListItem headerListItem) {
        headerWrapperView.setItems(headerListItem);
    }

    @Override
    public void setStarRating(double rating, int ratingsCount) {
        starRatingView.setRating(rating);
        exactRatingView.setText(String.valueOf(rating));
        reviewCountView.setText(String.valueOf(ratingsCount));
    }

    @Override
    public void setPriceLevel(int priceLevel, String label) {
        dotSeparatorView.setVisibility(priceLevel == 0 ? View.INVISIBLE : View.VISIBLE);
        priceLevelView.setText(label);
    }

    @Override
    public void setVenueSizeView(String venueSizeText) {
        venueSizeView.setText(venueSizeText);
    }

    @Override
    public void setDressCodeView(String dressCodeText) {
        dressCodeView.setText(dressCodeText);
    }

}
