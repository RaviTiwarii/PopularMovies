package com.android.example.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<Review> reviews;

    public ReviewAdapter(@NonNull List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews.clear();
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorTextView;
        private final TextView reviewTextView;

        ViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_author);
            reviewTextView = itemView.findViewById(R.id.tv_review);
        }

        void bind(@NonNull Review review) {
            authorTextView.setText(review.getAuthor());
            reviewTextView.setText(review.getContent());
        }
    }
}
