package com.ryanbarrett.upcoming_films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryanbarrett.upcoming_films.models.MoviePoster;

import java.util.List;
/**
 *  Class which is responsible for displaying the movies in a list. This binds the movies to the view.
 * */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MoviePoster> movieImagePathsList;

    private MovieClickListener listener;

    public MovieAdapter(List<MoviePoster> movieImagePathsList, MovieClickListener movieClickListener) {
        this.movieImagePathsList = movieImagePathsList;
        this.listener = movieClickListener;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie, parent, false);
        MovieViewHolder vh = new MovieViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final MoviePoster moviePoster = movieImagePathsList.get(position);

        Glide.with(holder.movieImage.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + moviePoster.getPosterPath())
                .into(holder.movieImage);

        holder.movieTitle.setText(moviePoster.getMovieTitle());

        holder.moviePosterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.movieClickListener(moviePoster.getMovieId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieImagePathsList.size();
    }

    public void updateList(List<MoviePoster> newMovieList) {
        movieImagePathsList = newMovieList;
        notifyDataSetChanged();
    }

    /**
     *  The view representing each cell containing an individual movie.
     * */
    static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movieImage;
        TextView movieTitle;
        LinearLayout moviePosterLayout;

        MovieViewHolder(View view) {
            super(view);
            movieImage = view.findViewById(R.id.movie_image);
            movieTitle = view.findViewById(R.id.movieTitle);
            moviePosterLayout = view.findViewById(R.id.movie_layout);
        }
    }
}

interface MovieClickListener {
    void movieClickListener(Long movieId);
}