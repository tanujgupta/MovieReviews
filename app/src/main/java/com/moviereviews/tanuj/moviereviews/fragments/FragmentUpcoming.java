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

import static com.moviereviews.tanuj.moviereviews.network.Response.UPCOMING;
import static com.moviereviews.tanuj.moviereviews.network.Response.fetchJsonRequest;

public class FragmentUpcoming extends Fragment {

    private AdapterMovies adapterUpcoming;
    private RecyclerView listUpcoming;
    private TextView textVolleyError;
    private ProgressBar progressBar;

    public static FragmentUpcoming newInstance() {
        FragmentUpcoming fragment = new FragmentUpcoming();
        return fragment;
    }

    public FragmentUpcoming() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        fetchJsonRequest(adapterUpcoming, textVolleyError, progressBar, UPCOMING);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_item_row, container, false);

        listUpcoming = (RecyclerView) view.findViewById(R.id.listMovieHits);
        textVolleyError= (TextView) view.findViewById(R.id.textVolleyError);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        listUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterUpcoming =  new AdapterMovies(getActivity());
        listUpcoming.setAdapter(adapterUpcoming);

        fetchJsonRequest(adapterUpcoming, textVolleyError, progressBar, UPCOMING);

        return view;
    }


}
