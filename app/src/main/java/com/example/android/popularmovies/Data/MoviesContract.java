package com.example.android.popularmovies.Data;

import android.provider.BaseColumns;

/**
 * Created by kugar on 02/23/18.
 */

public class MoviesContract {

    public static final class moviesEntry implements BaseColumns{
        public static final String TABLE_NAME="tableMovies";
        public static final String COLUMN_ORIGINAL_TITLE="title";
        public static final String COLUMN_IMAGE_PATH="image";
        public static final String COLUMN_PLOT_SYNOPSIS="overview";
        public static final String COLUMN_USER_RATING="rating";
        public static final String COLUMN_RELEASE_DATE="rdate";
    }

}
