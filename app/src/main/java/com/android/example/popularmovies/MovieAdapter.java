package com.android.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    @NonNull
    private List<Movie> mMovies;

    @NonNull
    private OnListItemClickListener mListener;

    public interface OnListItemClickListener {
        void onClick(@NonNull Movie movie);
    }

    public MovieAdapter(@NonNull final List<Movie> movies,
                        @NonNull final OnListItemClickListener listener) {
        mMovies = movies;
        mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(@NonNull final List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMoviePosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        public void bind(final Movie movie) {
            Picasso.with(mMoviePosterImageView.getContext())
                    .load(movie.getPosterPath())
                    .into(mMoviePosterImageView);
        }

        @Override
        public void onClick(View view) {
            Movie movie = mMovies.get(getAdapterPosition());
            mListener.onClick(movie);
        }
    }
}
