package com.moviereviews.tanuj.moviereviews.activities;


import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.moviereviews.tanuj.moviereviews.R;

public class DetailedMovieInfo extends ActionBarActivity {

    ImageView movieThumbnail;
    TextView movieTitle, movieReleaseDate, synopsis;
    RatingBar movieAudienceScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_movie_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();


        Bundle bundle = getIntent().getExtras();
        String movieName = bundle.getString("movie_name");
        String synopsis_text = bundle.getString("synopsis");
        String urlThumbNail = bundle.getString("url");
        String dateRelease = bundle.getString("date");
        int rating = bundle.getInt("rating");

        movieThumbnail = (ImageView) findViewById(R.id.movieThumbnail);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        synopsis = (TextView) findViewById(R.id.synopsis);
        movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        movieAudienceScore = (RatingBar) findViewById(R.id.movieAudienceScore);

        movieTitle.setText(movieName);
        movieReleaseDate.setText(dateRelease);
        synopsis.setText(synopsis_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
