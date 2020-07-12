package com.easy2learn.game.ui.topic.games;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easy2learn.game.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameOptionsFragment extends Fragment {

    private Button memoryBtn;
    private Button anagramsBtn;
    private Button quizBtn;


    public GameOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memoryBtn = view.findViewById(R.id.button_memory);
        memoryBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate( R.id.action_gameOptionsFragment_to_memoryFragment);
        });

        anagramsBtn = view.findViewById(R.id.button_anagrams);
        anagramsBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate( R.id.action_gameOptionsFragment_to_anagramsFragment);

        });

        quizBtn = view.findViewById(R.id.button_quiz);
        quizBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate( R.id.action_gameOptionsFragment_to_quizFragment);
        });
    }
}
