package com.example.nightclubpicker.nearby_places;

import com.example.nightclubpicker.common.BasePresenter;
import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.models.SearchResult;

import java.util.List;

public interface NearbyPlacesContract {
    interface View {
        void updateListItems(List<ListItem> listItems);

        void notifyListInsertion(int position);

        void navigateToPlaceDetails(SearchResult result);

        boolean hasLocationPermission();

        void setLoadingSpinnerVisibility(boolean isVisible);
    }

    interface Presenter extends BasePresenter {
        void updateResults(int lastVisibleItemPosition);
    }
}
