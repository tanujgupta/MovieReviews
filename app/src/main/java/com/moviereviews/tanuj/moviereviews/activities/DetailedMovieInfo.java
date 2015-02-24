package com.moviereviews.tanuj.moviereviews.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.Constants;
import com.moviereviews.tanuj.moviereviews.network.VolleySingleton;

public class DetailedMovieInfo extends ActionBarActivity {

    private ImageView movieThumbnail;
    private TextView movieTitle, movieReleaseDate, synopsis;
    private RatingBar movieAudienceScore;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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

        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();

        movieTitle.setText(movieName);
        movieReleaseDate.setText(dateRelease);
        synopsis.setText(synopsis_text);

        synopsis.setMovementMethod(new ScrollingMovementMethod());

        if (rating == -1) {

            movieAudienceScore.setRating(0.0F);
            movieAudienceScore.setAlpha(0.5F);

        } else {

            movieAudienceScore.setRating(rating / 20.0F);
            movieAudienceScore.setAlpha(1.0F);

        }

        loadImages(urlThumbNail);

    }

    private void loadImages(String urlThumbnail) {

        if (!urlThumbnail.equals(Constants.NA)) {

            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    movieThumbnail.setImageBitmap(response.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home)
        {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
