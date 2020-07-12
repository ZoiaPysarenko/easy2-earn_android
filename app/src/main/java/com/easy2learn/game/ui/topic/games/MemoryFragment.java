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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy2learn.game.MainActivity;
import com.easy2learn.game.R;
import com.easy2learn.game.ui.topic.VocabularyItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class MemoryFragment extends Fragment {

    private int firstPosition = -1;
    private int firstItemID;
    private int itemsOpened = 0;
    private int matchesNum = 0;
    private int curCategory;

    ArrayList<MemoryItem> gameItems;
    GridView gridView;
    MemoryGameAdapter memoryGameAdapter;

    Dialog congrPopup;

    public MemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init
        congrPopup = new Dialog(getContext());

        Intent intent = getActivity().getIntent();
        ArrayList<VocabularyItem> data = intent.getExtras().getParcelableArrayList("Topic data");
        curCategory = data.get(0).getCategory();
//        for (int i = 0; i < data.size(); i++) {
//            System.out.println(data.get(i));
//        }

        gameItems = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MemoryItem curImgItem = new MemoryItem(data.get(i).getId(), true, data.get(i).getUrl());
            MemoryItem curTextItem = new MemoryItem(data.get(i).getId(), false, data.get(i).getWord());
            gameItems.add(curImgItem);
            gameItems.add(curTextItem);
        }

        gridView = view.findViewById(R.id.memoryGridView);
        memoryGameAdapter = new MemoryGameAdapter(getContext(), curCategory);
//        gridView.setAdapter(memoryGameAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (itemsOpened >= 2) return;

                ImageView cover = view.findViewById(R.id.memoryImageViewCover);
                cover.setVisibility(View.GONE);


                if (firstPosition == -1){
                    firstPosition = position;
                    firstItemID = gameItems.get(position).getId();
                    itemsOpened++;
                }else if(firstPosition == position){
                    cover.setVisibility(View.VISIBLE);
                    firstPosition = -1;
                    firstItemID = -1;
                    itemsOpened--;
                }else{
                    int secondItemID = gameItems.get(position).getId();
                    itemsOpened++;
                    if (secondItemID == firstItemID){
                        gameItems.get(firstPosition).setEnabled(false);
                        gameItems.get(position).setEnabled(false);
                        firstPosition = -1;
                        firstItemID = -1;
                        itemsOpened = 0;
                        matchesNum++;
                        if (matchesNum == gameItems.size() / 2){
                            showCongrPopup(view);
                        }
                    }else {
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            cover.setVisibility(View.VISIBLE);
                            if (firstPosition >= 0){
                                gridView.getChildAt(firstPosition).findViewById(R.id.memoryImageViewCover).setVisibility(View.VISIBLE);
                            }
                            itemsOpened = 0;
                            firstPosition = -1;
                            firstItemID = -1;
                        }, 1000);
                    }
                }
            }
        });

        startNewGame();


    }


    private void startNewGame() {
        Collections.shuffle(gameItems);
        memoryGameAdapter.setMemoryItems(gameItems);
        gridView.setAdapter(memoryGameAdapter);
        firstPosition = -1;
        firstItemID = -1;
        itemsOpened = 0;
        matchesNum = 0;
        for (int i = 0; i < gameItems.size(); i++) {
            gameItems.get(i).setEnabled(true);
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
                startNewGame();
                congrPopup.dismiss();
            }
        });
        congrPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congrPopup.show();

    }


}
