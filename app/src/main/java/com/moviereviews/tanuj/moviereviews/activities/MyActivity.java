package com.moviereviews.tanuj.moviereviews.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;

import com.moviereviews.tanuj.moviereviews.R;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentBoxOffice;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentSearch;
import com.moviereviews.tanuj.moviereviews.fragments.FragmentUpcoming;
import com.moviereviews.tanuj.moviereviews.fragments.NavigationDrawerFragment;
import com.moviereviews.tanuj.moviereviews.views.SlidingTabLayout;


public class MyActivity extends ActionBarActivity {

    public static final int MOVIES_SEARCH_RESULTS=0;
    public static final int MOVIES_HITS=1;
    public static final int MOVIES_UPCOMING=2;
    private Toolbar toolbar;
    private SlidingTabLayout mTabs;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment =  (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {super(fm);}

        public Fragment getItem(int num) {

            Fragment fragment=null;

            switch (num){

                case MOVIES_SEARCH_RESULTS:
                    fragment= FragmentSearch.newInstance("", "");
                    break;

                case MOVIES_HITS:
                    fragment= FragmentBoxOffice.newInstance();
                    break;

                case MOVIES_UPCOMING:
                    fragment= FragmentUpcoming.newInstance("", "");
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
//        private Drawable getIcon(int position) {
//            return getResources().getDrawable(icons[position]);
//        }
    }

}
