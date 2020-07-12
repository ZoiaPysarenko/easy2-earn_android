package com.easy2learn.game.ui.topic;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


class SwipePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<VocabularyItem> data;
    private boolean isFirstPronounced = false;

    public SwipePagerAdapter(FragmentManager fm, ArrayList<VocabularyItem> data) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.data = data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Fragment getItem(int position) {
        VocabularyItem item = data.get(position);
        Fragment curFragment = VocabularyGalleryFragment.newInstance(item, position == 0 && !isFirstPronounced);
        if(!isFirstPronounced && position == 0){
            isFirstPronounced = true;
        }
        return curFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }



}
