package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by kugar on 02/23/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

//    private int mNumberItems; //how many items the adapter will hold
    private Cursor mCursor;
    private Context mContext;
    private final ListItemClickListener mListItemClickListener;


    public interface ListItemClickListener{
        void onListItemClicked(int clickedItemIndex);
    }
    public MoviesAdapter(Cursor cursor, Context context, ListItemClickListener listItemClickListener){
//        mNumberItems=numberItems;
        mContext=context;
        mCursor=cursor;
        mListItemClickListener=listItemClickListener;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int listItemLayout=R.layout.list_item;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean shouldAttachNow=false;

        View view=layoutInflater.inflate(listItemLayout,parent,shouldAttachNow);
        MoviesViewHolder viewHolder=new MoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        //TODO CODE
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String title=mCursor.getString((mCursor.getColumnIndex(MoviesContract.moviesEntry.COLUMN_ORIGINAL_TITLE)));
        String image=mCursor.getString((mCursor.getColumnIndex(MoviesContract.moviesEntry.COLUMN_IMAGE_PATH)));
        Picasso.with(mContext).load(buildImagePath(image)).into(holder.imageView);
        holder.textView.setText(title);
    }

    private String buildImagePath(String image){
        return R.string.base + image + R.string.size;
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView imageView;
        final TextView textView;

        public MoviesViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.iv_list_item);
            textView=(TextView) view.findViewById(R.id.tv_list_item);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mListItemClickListener.onListItemClicked(adapterPosition);

//            mCursor.moveToPosition(adapterPosition);
//            long dateInMillis = mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
//            mClickHandler.onClick(dateInMillis);
        }


    }


}
