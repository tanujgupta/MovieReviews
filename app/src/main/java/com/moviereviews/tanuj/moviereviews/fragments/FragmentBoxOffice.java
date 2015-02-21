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

import static com.moviereviews.tanuj.moviereviews.network.Response.*;

public class FragmentBoxOffice extends Fragment implements SortListener{

    private AdapterMovies adapterBoxOffice;
    private RecyclerView listMovieHits;
    private TextView textVolleyError;
    private ProgressBar progressBar;

    public static FragmentBoxOffice newInstance() {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        return fragment;
    }

    public FragmentBoxOffice() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        fetchJsonRequest(adapterBoxOffice, textVolleyError, progressBar, BOX_OFFICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_item_row, container, false);

        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        textVolleyError= (TextView) view.findViewById(R.id.textVolleyError);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice =  new AdapterMovies(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        fetchJsonRequest(adapterBoxOffice, textVolleyError, progressBar, BOX_OFFICE);

        return view;
    }

    @Override
    public void sort(int sortby) {
        adapterBoxOffice.sortMoview(sortby);
    }

}
