package com.moviereviews.tanuj.moviereviews;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    //TODO change this key
    public static final String API_KEY_ROTTEN_TOMATOES="k8ep8wy2wp7rjmywy4rfwwre";

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