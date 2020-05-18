package com.example.nightclubpicker.nearby_places;

import com.example.nightclubpicker.common.list_items.ListItem;
import com.example.nightclubpicker.nearby_places.models.SearchResult;

import java.util.List;

public interface PlacesContract {
    interface View {
        void updateListItems(List<ListItem> listItems);

        void notifyListInsertion(int position);

        void navigateToPlaceDetails(SearchResult result);

        boolean hasLocationPermission();

        void setLoadingSpinnerVisibility(boolean isVisible);

        void setLoading(boolean isLoading);
    }

    interface Presenter {

        void onStart();

        void fetchLocation();

        void loadMoreResults();

        int getListSize();
    }
}
