package com.moviereviews.tanuj.moviereviews.extras;

/*
Interface storing json keys for rotten tomatoes api
 */
public interface Keys {

    public interface EndpointBoxOffice{

        public static final String KEY_MOVIES="movies";
        public static final String KEY_ID="id";
        public static final String KEY_TITLE="title";
        public static final String KEY_RELEASE_DATES="release_dates";
        public static final String KEY_THEATER="theater";
        public static final String KEY_RATINGS="ratings";
        public static final String KEY_AUDIENCE_SCORE="audience_score";
        public static final String KEY_SYNOPSIS="synopsis";
        public static final String KEY_POSTERS="posters";
        public static final String KEY_THUMBNAIL="thumbnail";

    }
}