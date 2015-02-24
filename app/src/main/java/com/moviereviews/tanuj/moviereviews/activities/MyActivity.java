package com.moviereviews.tanuj.moviereviews.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.extras.SortListener;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentBoxOffice;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentInTheatres;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentUpcoming;
import com.moviereviews.tanuj.moviereviews.fragments.NavigationDrawerFragment;
import com.moviereviews.tanuj.moviereviews.views.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import static com.moviereviews.tanuj.moviereviews.extras.Resources.*;


public class MyActivity extends ActionBarActivity {

    public static final int MOVIES_IN_THEATRE = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_UPCOMING = 2;

    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATINGS = "sortRatings";

    private Toolbar toolbar;
    private SlidingTabLayout mTabs;
    private ViewPager mPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);

        buildFAB();

    }

    private void buildFAB() {

        //define the icon for the main floating action button
        ImageView iconActionButton = new ImageView(this);
        iconActionButton.setImageResource(R.drawable.ic_action_new);

        //set the appropriate background for the main floating action button along with its icon
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(iconActionButton)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        //define the icons for the sub action buttons
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_important);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        //build the sub buttons
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();
        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortName.setOnClickListener(clickListner);
        buttonSortDate.setOnClickListener(clickListner);
        buttonSortRatings.setOnClickListener(clickListner);

        //add the sub buttons to the main floating action button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(actionButton)
                .build();
    }

    private View.OnClickListener clickListner =  new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Fragment fragment= (Fragment) adapter.instantiateItem(mPager, mPager.getCurrentItem());

            if(fragment instanceof SortListener){

                if (v.getTag().equals(TAG_SORT_NAME)) {
                    ((SortListener) fragment).sort(SORT_BY_NAME);
                }
                if (v.getTag().equals(TAG_SORT_DATE)) {
                    ((SortListener) fragment).sort(SORT_BY_DATE);
                }
                if (v.getTag().equals(TAG_SORT_RATINGS)) {
                    ((SortListener) fragment).sort(SORT_BY_STAR);
                }

            }

        }
    };

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {

            Fragment fragment = null;

            switch (num) {

                case MOVIES_IN_THEATRE:
                    fragment = FragmentInTheatres.newInstance();
                    break;

                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance();
                    break;

                case MOVIES_UPCOMING:
                    fragment = FragmentUpcoming.newInstance();
                    break;
            }

            return fragment;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }
    }

    @Override
    protected void onResume() {

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        super.onResume();
    }
}
