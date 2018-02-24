package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.Data.MoviesContract.moviesEntry;

/**
 * Created by kugar on 02/23/18.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="movies.db";
    private static final int DB_VERSION=1;

    public MoviesDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE="CREATE TABLE "+
                moviesEntry.TABLE_NAME+" ("+
                moviesEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                moviesEntry.COLUMN_ORIGINAL_TITLE+ "TEXT NOT NULL, "+
                moviesEntry.COLUMN_IMAGE_PATH+" , "+
                moviesEntry.COLUMN_PLOT_SYNOPSIS+" , "+
                moviesEntry.COLUMN_USER_RATING+" , "+
                moviesEntry.COLUMN_RELEASE_DATE+" );";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ moviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
