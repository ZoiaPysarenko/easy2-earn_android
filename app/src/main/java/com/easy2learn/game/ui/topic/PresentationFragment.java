package com.easy2learn.game.ui.topic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easy2learn.game.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment extends Fragment {
    ViewPager pagerSwipe;

    private Button startGameBtn;


    public PresentationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_presentation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        startGameBtn = view.findViewById(R.id.btn_start_games);

        pagerSwipe = view.findViewById(R.id.pager_swipe);

        Intent intent = getActivity().getIntent();

        ArrayList<VocabularyItem> data = intent.getExtras().getParcelableArrayList("Topic data");
        pagerSwipe.setAdapter(new SwipePagerAdapter(getChildFragmentManager(), data));

        startGameBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate( R.id.action_presentationFragment_to_gameOptionsFragment);
        });
    }


}
