package com.ryanbarrett.upcoming_films;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.ryanbarrett.upcoming_films.models.MoviePoster;
import com.ryanbarrett.upcoming_films.models.Result;
import com.ryanbarrett.upcoming_films.models.UpcomingMovieEntity;
import com.ryanbarrett.upcoming_films.network.GetDataService;
import com.ryanbarrett.upcoming_films.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
* The main user interface and also the place where the main API call for the upcoming movies list is executed.
* */
public class MainActivity extends AppCompatActivity implements MovieClickListener {

    private ConstraintLayout viewLayout;

    private RecyclerView moviesReyclerView;
    private MovieAdapter movieAdapter;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getMoviesList();

        movieAdapter = new MovieAdapter(new ArrayList<MoviePoster>(), this);

        moviesReyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesReyclerView.setAdapter(movieAdapter);
    }

    private void init() {
        moviesReyclerView = findViewById(R.id.movies_reycler_view);
        viewLayout = findViewById(R.id.main_activity_layout);
    }

    private void getMoviesList() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        service.getAllUpcomingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Response<UpcomingMovieEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<UpcomingMovieEntity> upcomingMovieEntityResponse) {
                            Response<UpcomingMovieEntity> test = upcomingMovieEntityResponse;

                            if (upcomingMovieEntityResponse.isSuccessful()) {

                                List<Result> results = upcomingMovieEntityResponse.body().getResults();

                                List<MoviePoster> posters = new ArrayList<>();
                                for (int i = 0; i < results.size(); i++) {
                                    Result result = results.get(i);
                                    posters.add(new MoviePoster(result.getId(), result.getPosterPath(), result.getTitle()));
                                }

                                movieAdapter.updateList(posters);

                            } else {
                                showError("oops something went wrong. Please try again.");
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);
                        showError("oops something went wrong. Please try again.");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void showError(String error) {
        Snackbar.make(viewLayout, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void movieClickListener(Long movieId) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        myIntent.putExtra("movieId", movieId); //Optional parameters
        startActivity(myIntent);
    }
}
