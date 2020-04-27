package com.ryanbarrett.upcoming_films.models;

public class MoviePoster {

    private String posterPath;
    private String movieTitle;
    private Long movieId;

    public MoviePoster(Long movieId, String posterPath, String movieTitle) {
        this.posterPath = posterPath;
        this.movieTitle = movieTitle;
        this.movieId = movieId;
    }

    public MoviePoster(Long movieId, String movieTitle) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public MoviePoster(Long movieId) {
        this.movieId = movieId;
        this.posterPath = "";
        this.movieTitle = "";
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setMovieTitle(String movieTitle) {
        this.setMovieTitle(movieTitle);
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
