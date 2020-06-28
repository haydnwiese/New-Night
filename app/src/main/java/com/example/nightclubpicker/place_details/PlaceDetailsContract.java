package com.example.nightclubpicker.place_details;

import android.net.Uri;

import com.example.nightclubpicker.common.BasePresenter;
import com.example.nightclubpicker.common.list_items.HeaderListItem;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.models.Photo;

import java.util.List;

public interface PlaceDetailsContract {
    interface View {

        void initViewPager(List<Photo> photos);

        void updateListItems(List<ListItem> items);

        void setHeaderWrapperView(HeaderListItem headerListItem);

        void setStarRating(double rating, int ratingsCount);

        void setPriceLevel(int priceLevel, String label);

        void setVenueSizeView(String venueSizeText);

        void setDressCodeView(String dressCodeText);
    }

    interface Presenter extends BasePresenter {

    }
}
