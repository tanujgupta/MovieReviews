package com.moviereviews.tanuj.moviereviews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviereviews.tanuj.moviereviews.Adapters.AdapterBoxOffice;
import com.moviereviews.tanuj.moviereviews.R;
import static com.moviereviews.tanuj.moviereviews.network.Response.sendJsonRequest;

public class FragmentBoxOffice extends Fragment {

    private AdapterBoxOffice adapterBoxOffice;
    private RecyclerView listMovieHits;
    private TextView textVolleyError;


    public static FragmentBoxOffice newInstance() {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        sendJsonRequest(adapterBoxOffice, textVolleyError);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);

        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        textVolleyError= (TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice =  new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        sendJsonRequest(adapterBoxOffice, textVolleyError);

        return view;
    }


}
