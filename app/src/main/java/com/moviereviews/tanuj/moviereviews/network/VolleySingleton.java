package com.moviereviews.tanuj.moviereviews.network;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.moviereviews.tanuj.moviereviews.MyApplication;

public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){

        mRequestQueue=Volley.newRequestQueue(MyApplication.getAppContext());

        mImageLoader=new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache=new LruCache<String, Bitmap>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap mBitmap) {
                cache.put(url, mBitmap);
            }
        });
    }

    public static VolleySingleton getInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

}