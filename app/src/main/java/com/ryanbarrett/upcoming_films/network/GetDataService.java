package com.ryanbarrett.upcoming_films.network;

import com.ryanbarrett.upcoming_films.models.MovieEntity;
import com.ryanbarrett.upcoming_films.models.UpcomingMovieEntity;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Represents the HTTP requests we will be making
 * */
public interface GetDataService {

    @GET("movie/upcoming?api_key=INSERT-API-KEY&language=en-US&page=1")
    Observable<Response<UpcomingMovieEntity>> getAllUpcomingMovies();

    @GET("movie/{movie_id}?api_key=INSERT-API-KEY&language=en-US")
    Observable<Response<MovieEntity>> getMovieDetails(@Path("movie_id") Long movieId);
}
