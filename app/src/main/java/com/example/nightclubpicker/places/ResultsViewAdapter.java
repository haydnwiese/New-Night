package com.example.nightclubpicker.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.places.models.SearchResult;

import java.util.List;

public class ResultsViewAdapter extends RecyclerView.Adapter<ResultsViewAdapter.ResultsViewHolder>{

    static class ResultsViewHolder extends RecyclerView.ViewHolder {
        private ImageView placeImageView;
        private TextView nameTextView;

        private ResultsViewHolder(View itemView) {
            super(itemView);

            placeImageView = (ImageView) itemView.findViewById(R.id.resultImage);
            nameTextView = (TextView) itemView.findViewById(R.id.resultName);
        }
    }

    private List<SearchResult> searchResults;

    public ResultsViewAdapter(List<SearchResult> results) {
        searchResults = results;
    }

    public void setListItems(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View resultView = inflater.inflate(R.layout.results_view_list_item, parent, false);

        return new ResultsViewHolder(resultView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);
        // TODO: Populate image view with correct image
        ImageView placeImageView = holder.placeImageView;
        placeImageView.setImageResource(R.drawable.ic_launcher_background);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(searchResult.getName());
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }
}
