package com.moviereviews.tanuj.moviereviews.network;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviereviews.tanuj.moviereviews.Adapters.AdapterMovies;
import com.moviereviews.tanuj.moviereviews.MyApplication;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.Constants;
import com.moviereviews.tanuj.moviereviews.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.moviereviews.tanuj.moviereviews.extras.Constants.NA;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_BOX_OFFICE;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_IN_THEATERS;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_PARAM_LIMIT;
import static com.android.volley.Response.*;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.URL_UPCOMING;

public class Response {

    public static final int BOX_OFFICE = 1;
    public static final int IN_THEARE = 0;
    public static final int UPCOMING = 2;
    private static final int LIMIT = 50;

    private static VolleySingleton volleySingleton;
    private static RequestQueue requestQueue;
    private static ArrayList<Movie> listMovies = new ArrayList<Movie>();
    private static DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");

    private static void init()
    {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    public static void fetchJsonRequest(final AdapterMovies adapterBoxOffice, final TextView eTextView, int url_type)
    {

        init();

        String url = "";

        switch (url_type)
        {
            case IN_THEARE  : url = getInTheatresURL();
                              break;

            case BOX_OFFICE : url = getBoxOfficetUrl();
                              break;

            case UPCOMING   : url = getUpcomingUrl();
                              break;

        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response)
            {
                eTextView.setVisibility(View.GONE);
                listMovies = parseJSONResponse(response);
                adapterBoxOffice.setMovieList(listMovies);
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                handleVolleyError(eTextView, error);
            }
        });

        requestQueue.add(request);

    }

    private static void handleVolleyError(TextView eTextView, VolleyError error)
    {
        eTextView.setVisibility(View.VISIBLE);

        if (error instanceof TimeoutError || error instanceof NoConnectionError)
        {
            eTextView.setText(R.string.error_timeout);
        } else if (error instanceof AuthFailureError)
        {
            eTextView.setText(R.string.error_auth_failure);

        } else if (error instanceof ServerError)
        {
            eTextView.setText(R.string.error_auth_failure);

        } else if (error instanceof NetworkError)
        {
            eTextView.setText(R.string.error_network);

        } else if (error instanceof ParseError)
        {
            eTextView.setText(R.string.error_parser);
        }
    }

    private static ArrayList<Movie> parseJSONResponse(JSONObject response)
    {

        ArrayList<Movie> listMovies = new ArrayList<Movie>();

        if (response != null && response.length() > 0) {


            try {

                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++)
                {
                    long id = -1;
                    String title = NA;
                    String releaseDate = NA;
                    int audienceScore = -1;
                    String synopsis = NA;
                    String urlThumbnail = NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    if (currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID))
                    {
                        id = currentMovie.getLong(KEY_ID);
                    }

                    if (currentMovie.has(KEY_TITLE) && !currentMovie.isNull(KEY_TITLE))
                    {
                        title = currentMovie.getString(KEY_TITLE);
                    }

                    //get the date in theaters for the current movie
                    if (currentMovie.has(KEY_RELEASE_DATES) && !currentMovie.isNull(KEY_RELEASE_DATES))
                    {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (objectReleaseDates != null && objectReleaseDates.has(KEY_THEATER) && !objectReleaseDates.isNull(KEY_THEATER))
                        {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }

                    //get the audience score for the current movie
                    JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);

                    if (objectRatings.has(KEY_AUDIENCE_SCORE) && !currentMovie.isNull(KEY_RATINGS))
                    {
                        if (objectRatings != null && objectRatings.has(KEY_AUDIENCE_SCORE) && !objectRatings.isNull(KEY_AUDIENCE_SCORE))
                        {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }

                    // get the synopsis of the current movie
                    if (currentMovie.has(KEY_SYNOPSIS) && !currentMovie.isNull(KEY_SYNOPSIS))
                    {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }

                    if (currentMovie.has(KEY_POSTERS) && !currentMovie.isNull(KEY_POSTERS))
                    {
                        JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);

                        if (objectPosters != null && objectPosters.has(KEY_THUMBNAIL) && !objectPosters.isNull(KEY_THUMBNAIL))
                        {
                            urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                        }
                    }

                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;

                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {}

                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    if(id!=-1 && !title.equals(Constants.NA))
                    {
                        listMovies.add(movie);
                    }
                }
            } catch (JSONException e) {}

        }

        return listMovies;

    }

    private static String getBoxOfficetUrl()
    {
        return URL_BOX_OFFICE + URL_CHAR_QUESTION + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES + URL_CHAR_AMEPERSAND + URL_PARAM_LIMIT + LIMIT;
    }

    private static String getUpcomingUrl()
    {
        return URL_UPCOMING + URL_CHAR_QUESTION + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES + URL_CHAR_AMEPERSAND + URL_PARAM_LIMIT + LIMIT;
    }

    private static String getInTheatresURL()
    {
        return URL_IN_THEATERS + URL_CHAR_QUESTION + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES + URL_CHAR_AMEPERSAND + URL_PARAM_LIMIT + LIMIT;
    }

}
