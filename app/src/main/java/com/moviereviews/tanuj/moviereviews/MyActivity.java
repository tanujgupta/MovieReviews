package com.moviereviews.tanuj.moviereviews;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviereviews.tanuj.moviereviews.tabs.SlidingTabLayout;

import org.w3c.dom.Text;


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
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);


    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment =  MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment {

        private TextView textView;

        public static MyFragment getInstance(int position) {

            MyFragment myFragment =  new MyFragment();
            Bundle args =  new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.position);

            Bundle args = getArguments();
            if (args != null) {
                textView.setText("The page selected is " + args.getInt("position"));
            }
            return layout;
        }
    }


}
