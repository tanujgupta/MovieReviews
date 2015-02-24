package com.moviereviews.tanuj.moviereviews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviereviews.tanuj.moviereviews.fragments.NavigationDrawerFragment;
import com.moviereviews.tanuj.moviereviews.model.Information;
import com.moviereviews.tanuj.moviereviews.R;

import java.util.Collections;
import java.util.List;


public class RVCustomAdapter extends RecyclerView.Adapter <RVCustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Information> data = Collections.emptyList();


    public RVCustomAdapter(Context context, List<Information> data) {

        mContext = context;
        inflater =  LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyViewHolder holder =  new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        Information current = data.get(position);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.iconId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {

            super(itemView);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (getPosition()) {

                case 0 : NavigationDrawerFragment.closeDrawer();
                    break;

                case 1 :
                    String appPackageName = mContext.getPackageName();

                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

                    } catch (android.content.ActivityNotFoundException anfe) {

                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));

                    }
                    break;

                case 2 :
                    Intent intent =  new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"tanujgup@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "MovieReviews Feedback");
                    intent.putExtra(Intent.EXTRA_TEXT, "");

                    try {

                        mContext.startActivity(Intent.createChooser(intent, "Send mail..."));

                    } catch (android.content.ActivityNotFoundException ex) {

                        Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }

        }
    }
}
