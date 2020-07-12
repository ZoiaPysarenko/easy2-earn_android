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
import android.widget.ImageView;
import android.widget.TextView;

import com.easy2learn.game.FirebaseStorageUtility;
import com.easy2learn.game.MainActivity;
import com.easy2learn.game.R;
import com.easy2learn.game.ui.topic.Utils;
import com.easy2learn.game.ui.topic.VocabularyItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class QuizFragment extends Fragment {

    private ImageView taskImg;
    private TextView congrMessage;
    private List<Button> buttonList;

    String[] congrats = {"Awesome!", "Brilliant!", "Good job!", "Well done!", "Fabulous!"};

    ArrayList<VocabularyItem> sourceData;
    ArrayList<VocabularyItem> data;
    VocabularyItem curItem;
    Random r;
    Dialog congrPopup;
    int score = 0;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        r = new Random();
        congrPopup = new Dialog(getContext());

        taskImg = view.findViewById(R.id.question_image);
        congrMessage = view.findViewById(R.id.congrat_message);

        buttonList = new ArrayList<>();
        buttonList.add(0, view.findViewById(R.id.button1));
        buttonList.add(1, view.findViewById(R.id.button2));
        buttonList.add(2, view.findViewById(R.id.button3));
        buttonList.add(3, view.findViewById(R.id.button4));

        buttonList.forEach(btn -> {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(btn, v);
                }
            });
        });

        Intent intent = getActivity().getIntent();
        sourceData = intent.getExtras().getParcelableArrayList("Topic data");
        data = (ArrayList<VocabularyItem>) sourceData.clone();


        showNextTask();
    }

    public void showNextTask(){
        congrMessage.setVisibility(View.GONE);
        Collections.shuffle(data);
        curItem = data.get(0);
        List<String> answers = curItem.getQuiz();
        Collections.shuffle(answers);
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.btn_gradient_style));
            buttonList.get(i).setText(answers.get(i));
        }
        FirebaseStorageUtility.getImage(taskImg, curItem.getCategory(), curItem.getUrl());

    }

    public void checkAnswer(Button button, View view){

        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setEnabled(false);
        }

        if (button.getText().equals(curItem.getWord())){
//            button.setBackgroundColor(Color.parseColor("#32CD32"));
            button.setBackground(getContext().getResources().getDrawable(R.drawable.btn_success_style));
            congrMessage.setText(congrats[r.nextInt(congrats.length)]);
            congrMessage.setVisibility(View.VISIBLE);
            score++;
        }else {
//            button.setBackgroundColor(Color.parseColor("#B22222"));
            button.setBackground(getContext().getResources().getDrawable(R.drawable.btn_error_style));
            findCorrectAnswer();
        }
        if (data.size() > 1){
            data.remove(curItem);
            Utils.setTimeout(() -> showNextTask(), 1000);
        }else {
            showCongrPopup(view);
        }

    }

    public void findCorrectAnswer(){
        for (int i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i).getText().equals(curItem.getWord())){
//                buttonList.get(i).setBackgroundColor(Color.parseColor("#32CD32"));
                buttonList.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.btn_success_style));
                return;
            }
        }
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
                showNextTask();
                congrPopup.dismiss();
            }
        });
        congrPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congrPopup.show();

    }
}
