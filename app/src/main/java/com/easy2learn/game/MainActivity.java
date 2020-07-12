package com.easy2learn.game;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.easy2learn.game.ui.topic.Category;
import com.easy2learn.game.ui.topic.Topic;
import com.easy2learn.game.ui.topic.TopicsViewAdapter;
import com.easy2learn.game.ui.topic.Utils;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    List<Topic> lstTopics;
    Object timer = null;
    ArrayList<Category> categories;

    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = this;
        setContentView(R.layout.activity_main);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        FirebaseStorageUtility.Init();
//        FirebaseStorageUtility.getInstance();
        FirebaseDAO.getDatabase().Init();

        timer = Utils.setTimeout(() -> Task(mContext), 1000);

    }

    private void Task(Context ctx){
        if(FirebaseDAO.getDatabase().isDatabaseReady()){

            categories = FirebaseDAO.getDatabase().getCategories();

            RecyclerView myTopicsRv = (RecyclerView) findViewById(R.id.topics_list_view_id);
            TopicsViewAdapter myTopicsAdapter = new TopicsViewAdapter(ctx, categories);
            myTopicsRv.setLayoutManager(new GridLayoutManager(ctx, 2));
            myTopicsRv.setAdapter(myTopicsAdapter);
            if(timer != null)
                Utils.clearTimeout(timer);
        }else{
            timer = Utils.setTimeout(() -> Task(ctx), 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact_us:
                Intent contactUsIntent = new Intent(this, ContactUsActivity.class);
                this.startActivity(contactUsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

