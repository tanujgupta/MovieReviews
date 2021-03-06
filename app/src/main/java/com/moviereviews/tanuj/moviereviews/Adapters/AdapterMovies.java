package com.moviereviews.tanuj.moviereviews.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.activities.DetailedMovieInfo;
import com.moviereviews.tanuj.moviereviews.extras.Constants;
import com.moviereviews.tanuj.moviereviews.extras.MovieSorter;
import com.moviereviews.tanuj.moviereviews.model.Movie;
import com.moviereviews.tanuj.moviereviews.network.VolleySingleton;

import static com.moviereviews.tanuj.moviereviews.extras.Resources.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
Adapter for all the three movie fragments with recycler view implementing the clicklistener. Sub activity is launched every
time an item on the recycler view is clicked
*/

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.ViewHolderBoxOffice>
{

    private ArrayList<Movie> listMovies = new ArrayList<Movie>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private Activity mActivity;

    public AdapterMovies(Activity activity)
    {
        mActivity = activity;
        layoutInflater = LayoutInflater.from(activity);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMovieList(ArrayList<Movie> listMovies)
    {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size()); // notify everytime the list items changes
    }


    // method called by main activity to sort the list in the selected fragment
    public void sortMoview(int sortBy) {

        switch (sortBy){

            case SORT_BY_NAME : MovieSorter.sortMoviesByName(listMovies);
                notifyDataSetChanged();
                break;

            case SORT_BY_DATE : MovieSorter.sortMoviesByDate(listMovies);
                notifyDataSetChanged();
                break;

            case SORT_BY_STAR : MovieSorter.sortMoviesByRating(listMovies);
                notifyDataSetChanged();
                break;
        }

    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // inflate the custom row which is passed to bindviewholder to bind data to the inflated layout
        View view = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBoxOffice holder, int position)
    {
        // set the values of inflated layout with the corresponding movie items
        Movie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        Date movieReleaseDate = currentMovie.getReleaseDateTheater();

        // set the date
        if (movieReleaseDate != null)
        {
            String formattedDate = dateFormatter.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formattedDate);

        } else {

            holder.movieReleaseDate.setText(Constants.NA);
       }

        int audienceScore = currentMovie.getAudienceScore();

        // set the rating on rating bar
        if (audienceScore == -1) {

            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);

        } else {

            holder.movieAudienceScore.setRating(audienceScore / 20.0F);
            holder.movieAudienceScore.setAlpha(1.0F);

        }

        // load thumbnail image
        String urlThumnail = currentMovie.getUrlThumbnail();
        loadImages(urlThumnail, holder);

    }

    // load the image. If an error exists while loading just use the default existing image on the layout
    private void loadImages(String urlThumbnail, final ViewHolderBoxOffice holder) {

        if (!urlThumbnail.equals(Constants.NA)) {

            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    holder.movieThumbnail.setImageBitmap(response.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return listMovies.size();

    }

    class ViewHolderBoxOffice extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {

            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // on item click start the sub-activity while passing data for the selected list item in the bundle
            Intent intent = new Intent(mActivity, DetailedMovieInfo.class);

            Date movieReleaseDate = listMovies.get(getPosition()).getReleaseDateTheater();
            String formattedDate;

            if (movieReleaseDate != null)
            {
                formattedDate = dateFormatter.format(movieReleaseDate);

            } else {

                formattedDate = Constants.NA;
            }

            Bundle mBundle = new Bundle();
            mBundle.putString("movie_name", listMovies.get(getPosition()).getTitle());
            mBundle.putString("synopsis", listMovies.get(getPosition()).getSynopsis());
            mBundle.putString("url", listMovies.get(getPosition()).getUrlThumbnail());
            mBundle.putString("date", formattedDate);
            mBundle.putInt("rating", listMovies.get(getPosition()).getAudienceScore());

            intent.putExtras(mBundle);

            mActivity.startActivity(intent);
        }
    }
}