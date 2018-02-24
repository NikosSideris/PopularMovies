package com.example.android.popularmovies;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.popularmovies.Utils.Utils;

public class SettingsActivity extends AppCompatActivity {

    Utils mUtils;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar=this.getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//        mUtils=new Utils();
//        mToast=new Toast(this);
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.rb_popularity:
//                if (checked)
//                    mUtils.ShowToast(mToast, "Menu Item"+String.valueOf(view.getId()),this);
//                    break;
//            case R.id.rb_ratings:
//                if (checked)
//                    mUtils.ShowToast(mToast, "Menu Item"+String.valueOf(view.getId()),this);
//                    break;
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


}
