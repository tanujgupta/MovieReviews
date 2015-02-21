package com.moviereviews.tanuj.moviereviews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moviereviews.tanuj.moviereviews.Adapters.AdapterMovies;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.SortListener;

import static com.moviereviews.tanuj.moviereviews.network.Response.IN_THEARE;
import static com.moviereviews.tanuj.moviereviews.network.Response.fetchJsonRequest;

public class FragmentInTheatres extends Fragment implements SortListener{

    private AdapterMovies adapterInTheatres;
    private RecyclerView listMovieTheatres;
    private TextView textVolleyError;
    private ProgressBar progressBar;

    public static FragmentInTheatres newInstance() {
        FragmentInTheatres fragment = new FragmentInTheatres();
        return fragment;
    }
    public FragmentInTheatres() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        fetchJsonRequest(adapterInTheatres, textVolleyError, progressBar, IN_THEARE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_item_row, container, false);

        listMovieTheatres = (RecyclerView) view.findViewById(R.id.listMovieHits);
        textVolleyError= (TextView) view.findViewById(R.id.textVolleyError);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        listMovieTheatres.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInTheatres =  new AdapterMovies(getActivity());
        listMovieTheatres.setAdapter(adapterInTheatres);

        fetchJsonRequest(adapterInTheatres, textVolleyError, progressBar, IN_THEARE);

        return view;
    }

    @Override
    public void sort(int sortby) {
            adapterInTheatres.sortMoview(sortby);
    }
}
