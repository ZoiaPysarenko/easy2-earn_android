package com.easy2learn.game.ui.topic;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;

import com.easy2learn.game.R;

public class TopicActivity extends AppCompatActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

//        //make the activity on full screen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_topic);

//        //hide the action bar
//        getSupportActionBar().hide();

    }

}
