package com.moviereviews.tanuj.moviereviews.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviereviews.tanuj.moviereviews.Log.L;
import com.moviereviews.tanuj.moviereviews.MyApplication;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.UrlEndpoints;
import com.moviereviews.tanuj.moviereviews.network.VolleySingleton;

import org.json.JSONObject;

public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;



    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                L.T(getActivity(), response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box_office, container, false);
    }

    public static String getRequestUrl(int limit) {

        return UrlEndpoints.URL_BOX_OFFICE
                + UrlEndpoints.URL_CHAR_QUESTION
                + UrlEndpoints.URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + UrlEndpoints.URL_CHAR_AMEPERSAND
                + UrlEndpoints.URL_PARAM_LIMIT + limit;

    }


}
