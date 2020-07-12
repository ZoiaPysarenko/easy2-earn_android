package com.easy2learn.game.ui.topic.games;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy2learn.game.FirebaseStorageUtility;
import com.easy2learn.game.MainActivity;
import com.easy2learn.game.R;
import com.easy2learn.game.ui.topic.Utils;
import com.easy2learn.game.ui.topic.VocabularyItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class AnagramsFragment extends Fragment {

    private TextView task;
    private EditText answer;
    private Button hintButton;
    private Button checkAnswer;
    private ImageView image;

    Random r;
    String currentWord;
    VocabularyItem curItem;
    ArrayList<VocabularyItem> sourceData;
    ArrayList<VocabularyItem> data;

    Dialog congrPopup;

    String[] congrats = {"Awesome!", "Brilliant!", "Good job!", "Well done!", "Fabulous!"};


    public AnagramsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anagrams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init
        congrPopup = new Dialog(getContext());

        task = view.findViewById(R.id.task);
        answer = view.findViewById(R.id.answer);
        hintButton = view.findViewById(R.id.hint_button);
        checkAnswer = view.findViewById(R.id.check_button);
        image = view.findViewById(R.id.anagram_image);


        r = new Random();

        Intent intent = getActivity().getIntent();
        sourceData = intent.getExtras().getParcelableArrayList("Topic data");
        data = (ArrayList<VocabularyItem>) sourceData.clone();
        Collections.shuffle(data);

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorageUtility.getImage(image, curItem.category, curItem.getUrl());
            }
        });

        checkAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (answer.getText().toString().equalsIgnoreCase(currentWord)){

                    String congrWord = congrats[r.nextInt(congrats.length)];
                    task.setText(congrWord);

                    FirebaseStorageUtility.getImage(image, curItem.category, curItem.getUrl());

                    //test
                    if (data.size() > 0){
                        for(int i = 0 ; i < data.size() ; i++){
                            if(data.get(i).getId() == curItem.getId()){
                                data.remove(i);
                                break;
                            }
                        }
                        Utils.setTimeout(() -> showNextWord(data), 2000);
                    }else{
                        showCongrPopup(v);
                    }


                }else {
                    task.setText("Sorry... Try again!");
                    task.setTextColor(Color.parseColor("#FF0000"));
                    Utils.setTimeout(() -> {
                        task.setText(shuffleLetters(currentWord));
                        task.setTextColor(Color.parseColor("#3EB642"));
                        answer.getText().clear();
                    }, 2000);

                }
                if (data.size() == 0){
                    showCongrPopup(v);
                }
            }
        });

        showNextWord(data);
    }


    private String shuffleLetters(String word){
        List<String> letters = Arrays.asList(word.split(""));
        Collections.shuffle(letters);
        String shuffledWord = "";
        for (String letter : letters) {
            shuffledWord += letter;
        }
        if (shuffledWord.equals(word)){
            return shuffleLetters(word);
        }
        return shuffledWord;
    }

    private void showNextWord(ArrayList<VocabularyItem> dataArr){
        curItem = dataArr.get(r.nextInt(dataArr.size()));
        currentWord = curItem.getWord();

        task.setText(shuffleLetters(currentWord));
        answer.setText("");
        image.setImageResource(R.drawable.question2);
        checkAnswer.setEnabled(true);

    }

    public void showCongrPopup(View v) {
        Button menuBtn;
        Button replayBtn;
        TextView closeText;

        congrPopup.setContentView(R.layout.custom_popup);
        menuBtn = congrPopup.findViewById(R.id.menu_btn);
        replayBtn = congrPopup.findViewById(R.id.replay_btn);
        closeText = congrPopup.findViewById(R.id.close_btn);

        closeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = (ArrayList<VocabularyItem>) sourceData.clone();
                Collections.shuffle(data);
                showNextWord(data);
                congrPopup.dismiss();
            }
        });
        congrPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congrPopup.show();

    }
}
