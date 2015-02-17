package com.moviereviews.tanuj.moviereviews.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviereviews.tanuj.moviereviews.Adapters.AdapterBoxOffice;
import com.moviereviews.tanuj.moviereviews.Log.L;
import static com.moviereviews.tanuj.moviereviews.extras.Keys.EndpointBoxOffice.*;
import static com.moviereviews.tanuj.moviereviews.extras.Constants.*;
import static com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints.*;
import com.moviereviews.tanuj.moviereviews.MyApplication;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.Constants;
import com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints;
import com.moviereviews.tanuj.moviereviews.model.Movie;
import com.moviereviews.tanuj.moviereviews.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentBoxOffice extends Fragment {

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovies = new ArrayList<Movie>();
    private DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
    private AdapterBoxOffice adapterBoxOffice;
    private RecyclerView listMovieHits;


    public static FragmentBoxOffice newInstance() {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);

        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice =  new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();

        return view;
    }

    private void sendJsonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(30),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listMovies = parseJSONResponse(response);
                        adapterBoxOffice.setMovieList(listMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.t(getActivity(), error.getMessage() + "");
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<Movie>();
        if (response != null && response.length() > 0) {
            long id = -1;
            String title = NA;
            String releaseDate = NA;
            int audienceScore = -1;
            String synopsis = NA;
            String urlThumbnail = NA;
            try {
                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
//get the id of the current movie
                    if (currentMovie.has(KEY_ID)
                            && !currentMovie.isNull(KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }
//get the title of the current movie
                    if (currentMovie.has(KEY_TITLE)
                            && !currentMovie.isNull(KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }
//get the date in theaters for the current movie
                    if (currentMovie.has(KEY_RELEASE_DATES)
                            && !currentMovie.isNull(KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);
                        if (objectReleaseDates != null
                                && objectReleaseDates.has(KEY_THEATER)
                                && !objectReleaseDates.isNull(KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }
//get the audience score for the current movie
                    JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                    if (objectRatings.has(KEY_AUDIENCE_SCORE)
                            && !currentMovie.isNull(KEY_RATINGS)) {
                        if (objectRatings != null
                                && objectRatings.has(KEY_AUDIENCE_SCORE)
                                && !objectRatings.isNull(KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }
// get the synopsis of the current movie
                    if (currentMovie.has(KEY_SYNOPSIS)
                            && !currentMovie.isNull(KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }
                    if (currentMovie.has(KEY_POSTERS)
                            && !currentMovie.isNull(KEY_POSTERS)) {
                        JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);
                        if (objectPosters != null
                                && objectPosters.has(KEY_THUMBNAIL)
                                && !objectPosters.isNull(KEY_THUMBNAIL)) {
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
                    if(id!=-1 && !title.equals(Constants.NA)) {
                        listMovies.add(movie);
                    }
                }
            } catch (JSONException e) {
            }

        }
        return listMovies;
    }

    public static String getRequestUrl(int limit) {

        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;

    }


}
