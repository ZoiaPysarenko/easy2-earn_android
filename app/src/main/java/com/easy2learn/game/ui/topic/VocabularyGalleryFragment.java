package com.easy2learn.game.ui.topic;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy2learn.game.FirebaseStorageUtility;
import com.easy2learn.game.R;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class VocabularyGalleryFragment extends Fragment {

    public static final String ARG_GALLERY_ITEM = "Gallery Item";
    private TextView vocabularyItem;
    private ImageView vocabularyImg;

    private VocabularyItem currentItem;
    TextToSpeech textToSpeech;

    private boolean isSpeakWithDelay = false;


    public static VocabularyGalleryFragment newInstance(VocabularyItem currentItem, boolean isSpeakWithDelay) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_GALLERY_ITEM, currentItem);
        VocabularyGalleryFragment fragment = new VocabularyGalleryFragment();
        fragment.setArguments(args);
        fragment.isSpeakWithDelay = isSpeakWithDelay;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary_gallery, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        vocabularyItem = view.findViewById(R.id.vocabulary_item);
        vocabularyImg = view.findViewById(R.id.vocabulary_img);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        Bundle args = getArguments();
        if (args == null) return;
        this.currentItem = args.getParcelable(ARG_GALLERY_ITEM);

        //Setting values
        vocabularyItem.setText(currentItem.getWord());
        FirebaseStorageUtility.getImage(vocabularyImg, currentItem.getCategory(), currentItem.getUrl());

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On resume method");
        if (isSpeakWithDelay){
            Utils.setTimeout(() -> {
                String word =  currentItem.getWord();
                textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
            }, 1000);
        }else{
            String word =  currentItem.getWord();
            textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
        }


    }

}
