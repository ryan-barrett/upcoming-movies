package com.ryanbarrett.upcoming_films.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Responsible for creating the retrofit instance which is used as an HTTP client.
 * */
public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
