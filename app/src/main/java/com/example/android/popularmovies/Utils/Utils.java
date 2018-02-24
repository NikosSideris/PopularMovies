package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kugar on 02/20/18.
 */

public class Utils {


    public void ShowToast(Toast toast, String message, Context context){
        if (toast !=null){
            toast.cancel();
        }
        toast=Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }


}
