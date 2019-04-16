package com.android.example.popularmovies.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private final List<Trailer> trailers;
    private final OnListItemClickListener<Trailer> listener;

    public TrailerAdapter(@NonNull final List<Trailer> trailers,
                          @NonNull final OnListItemClickListener<Trailer> listener) {
        this.trailers = trailers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.bind(trailer);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers.clear();
        this.trailers.addAll(trailers);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView trailerNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            trailerNameTextView = itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        void bind(@NonNull Trailer trailer) {
            trailerNameTextView.setText(trailer.getName());
        }

        @Override
        public void onClick(View v) {
            Trailer trailer = trailers.get(getAdapterPosition());
            listener.onClick(trailer);
        }
    }
}
