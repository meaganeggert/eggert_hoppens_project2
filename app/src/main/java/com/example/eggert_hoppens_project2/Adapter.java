package com.example.eggert_hoppens_project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<String> userSet;
    private String[] placeSet = {"1", "2", "3", "4", "5"};
    private ArrayList<String> scoreSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userTextView;
        private TextView scoreTextView;
        private TextView placeTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            userTextView = (TextView) view.findViewById(R.id.username_textView);
            placeTextView = (TextView) view.findViewById(R.id.place_textView);
            scoreTextView = (TextView) view.findViewById(R.id.score_textView);
        }

        public TextView getUserTextView() {
            return userTextView;
        }
        public TextView getScoreTextView() {
            return scoreTextView;
        }
        public TextView getPlaceTextView() {
            return placeTextView;
        }
    }

    public Adapter(ArrayList<String> users, ArrayList<String> scores) {
        userSet = users;
        scoreSet = scores;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getUserTextView().setText(userSet.get(position));
        viewHolder.getPlaceTextView().setText(placeSet[position]);
        viewHolder.getScoreTextView().setText(scoreSet.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userSet.size();
    }
}
