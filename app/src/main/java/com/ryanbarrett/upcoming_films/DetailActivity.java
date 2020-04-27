package com.ryanbarrett.upcoming_films;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.ryanbarrett.upcoming_films.models.MovieEntity;
import com.ryanbarrett.upcoming_films.network.GetDataService;
import com.ryanbarrett.upcoming_films.network.RetrofitClientInstance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
/**
 * The detail page user interface and the place where the movie detail API call is executed.
 * */
public class DetailActivity extends AppCompatActivity {

    private Long movieId;

    private TextView movieName;
    private TextView movieOverview;
    private ConstraintLayout viewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieId = getIntent().getLongExtra("movieId", 0);

        movieName = findViewById(R.id.movie_title);
        movieOverview = findViewById(R.id.overview_line);
        viewLayout = findViewById((R.id.view_layout));

        if (movieId != 0) {
            getMovie(movieId);
        } else {
            showError("Oops something went wrong");
        }

    }


    private void getMovie(Long id) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        service.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Response<MovieEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<MovieEntity> movieEntityResponse) {
                        if (movieEntityResponse.isSuccessful()) {

                            if (movieEntityResponse.body() != null) {
                                updateUI(movieEntityResponse.body());
                            }
                        } else {
                            // TODO: Error Handle
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO: Error Handle
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateUI(MovieEntity movie) {
        if (movie != null) {

            movieName.setText(movie.getTitle());

            movieOverview.setText(movie.getOverview());


        } else {
            showError("Oops something went wrong.");
        }
    }

    private void showError(String error) {
        Snackbar.make(viewLayout, error, Snackbar.LENGTH_LONG).show();
    }

}
