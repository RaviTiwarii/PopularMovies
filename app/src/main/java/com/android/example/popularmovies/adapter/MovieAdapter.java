package com.android.example.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    @NonNull
    private final List<Movie> movies;

    @NonNull
    private final OnListItemClickListener<Movie> listener;

    public MovieAdapter(@NonNull final List<Movie> movies,
                        @NonNull final OnListItemClickListener<Movie> listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_movies, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(@NonNull final List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mMoviePosterImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bind(final Movie movie) {
            Picasso.get().load(movie.getPosterPathUrl()).into(mMoviePosterImageView);
        }

        @Override
        public void onClick(View view) {
            Movie movie = movies.get(getAdapterPosition());
            listener.onClick(movie);
        }
    }
}
