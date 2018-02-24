package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.Data.MoviesContract;
import com.example.android.popularmovies.Data.MoviesDbHelper;
import com.example.android.popularmovies.Utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int SORT_POPULAR=0;
    private static final int SORT_RATING=1;
    private static String SORTBY_POPULARITY = new String();
    private String SORTBY_RATING = new String();
    private String SETTINGS = new String();
    private Toast mToast;
    private Utils mUtils; //helper var in order to use utils
    private static final int NUM_LIST_ITEMS = 20; //num of items to hold
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView mRecyclerView;
    private static final int SPAN_COUNT = 2; //spanCount for StaggeredGridLayoutManager
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private int mSortOrder;
    private String notJson=null;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SORTBY_POPULARITY = getString(R.string.sort_by_popularity);
        SORTBY_RATING = getString(R.string.sort_by_rating);
        SETTINGS = getString(R.string.settings);
        mContext=getApplicationContext();

//        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        mUtils = new Utils();
        mToast = new Toast(this);
//setup recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

//get shared preferences
        setupPreferences();
        refreshUI();

//use API

        URL apiResultUrl=buildURL(mSortOrder);
        new handleAPI().execute(apiResultUrl);


//get db
        MoviesDbHelper dbHelper = new MoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mCursor = getAllMovies();


        mMoviesAdapter = new MoviesAdapter(mCursor, this, this);
        mRecyclerView.setAdapter(mMoviesAdapter);


    }

    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            //TODO CODE
        if (sharedPreferences.getBoolean(getString(R.string.sort_by_rating), false)) {
            mSortOrder = SORT_RATING;
            //TODO CODE
        }
        if (!sharedPreferences.getBoolean(getString(R.string.sort_by_rating), false)) {
            mSortOrder = SORT_POPULAR;

        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.mi_settings){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        mUtils.ShowToast(mToast, "Item clicked=" + clickedItemIndex, this);
    }

    private Cursor getAllMovies() {
        return mDb.query(
                MoviesContract.moviesEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (key.equals(SORTBY_RATING)){
            if (sharedPreferences.getBoolean(key,true)){
                mSortOrder=SORT_RATING;
            }else{
                mSortOrder=SORT_POPULAR;
            }
        }
        refreshUI();
    }

    void refreshUI() {
        //TODO CODE
     }
    private Cursor getMainActivityMovies(){
        String[] selection={MoviesContract.moviesEntry.COLUMN_IMAGE_PATH, MoviesContract.moviesEntry.COLUMN_ORIGINAL_TITLE};
        return mDb.query(
            MoviesContract.moviesEntry.TABLE_NAME,selection,null,null,null,null,null
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    public class handleAPI extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL=urls[0];
            String API_response=null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(searchURL)
                    .build();
            try (Response response = client.newCall(request).execute()) {

                API_response=response.body().string();
            }catch (IOException e){
                API_response= null;
            }
            return API_response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!=null && !s.equals("")){
                //TODO CODE
                notJson=s;
            }
            mUtils.ShowToast(mToast,s,mContext);
            super.onPostExecute(s);
        }
    }

    private URL buildURL(int sortOrder){
        String s=new String();
        switch (sortOrder){
            case SORT_POPULAR:
                s=  getString(R.string.url_popularity) + getString(R.string.api) + getString(R.string.url_popularity_part2);
                break;
            case SORT_RATING:
                s= getString(R.string.url_vote_average) + getString(R.string.api) + getString(R.string.url_vote_average_part2);
                break;
        }
        try {
            URL url=new URL(s);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URL buildURL2(int sortOrder){
        String s=new String();
        switch (sortOrder){
            case SORT_POPULAR:
                s=  getString(R.string.uri_popularitysorting);// + getString(R.string.api) + getString(R.string.url_popularity_part2);
                break;
            case SORT_RATING:
                s= getString(R.string.uri_ratingsorting);// + getString(R.string.api) + getString(R.string.url_vote_average_part2);
                break;
        }
        Uri uri=Uri.parse(getString(R.string.uri_base)).buildUpon()
                .appendQueryParameter(getString(R.string.uri_apiquery),getString(R.string.api))
                .appendQueryParameter(getString(R.string.uri_sortquery),s)
                .build();
        URL url=null;
        try {
            url=new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        return url;
    }

}
