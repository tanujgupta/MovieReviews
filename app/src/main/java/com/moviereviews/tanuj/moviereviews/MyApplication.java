package com.moviereviews.tanuj.moviereviews;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    //TODO change this key
    public static final String API_KEY_ROTTEN_TOMATOES="54wzfswsa4qmjg8hjwa64d4c";

    private static MyApplication sInstance;

    public static MyApplication getInstance() {

        return sInstance;
    }
    public static Context getAppContext(){

        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

}