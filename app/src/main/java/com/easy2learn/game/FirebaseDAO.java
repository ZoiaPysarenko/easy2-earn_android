package com.easy2learn.game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.easy2learn.game.ui.topic.Category;
import com.easy2learn.game.ui.topic.VocabularyItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FirebaseDAO {

    private FirebaseDatabase database = null;
    private DatabaseReference refCategories = null;
    private DatabaseReference refVocabularies = null;
    private ArrayList<Category> categories = null;
    private ArrayList<VocabularyItem> vocabularies = null;

    private boolean categoriesReady = false;
    private boolean vocabularyReady = false;

    private FirebaseDAO(){}

    public static FirebaseDAO DATABASE = null;

    public static FirebaseDAO getDatabase() {
        if (DATABASE == null) {
            DATABASE = new FirebaseDAO();
        }
        return(DATABASE);
    }

    public boolean Init(){
        database = FirebaseDatabase.getInstance();

        refCategories = database.getReference("categories");
        refVocabularies = database.getReference("vocabularies");

        refCategories.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Category item = child.getValue(Category.class);
                    categories.add(item);
                }

                categoriesReady = true;
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        refVocabularies.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vocabularies = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    VocabularyItem item = child.getValue(VocabularyItem.class);
                    vocabularies.add(item);
                }

                vocabularyReady = true;
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return true;
    }

    public ArrayList<Category> getCategories(){
        if(categories != null)
            return categories;

        return null;
    }


    public ArrayList<VocabularyItem> getVocabulariesByCategoryId(int id){
        ArrayList<VocabularyItem> vCat = new ArrayList<>();
        vocabularies.forEach(v -> {
            if(v.category == id)
                vCat.add(v);
        });
        return vCat;
    }

    public boolean isDatabaseReady(){
        return categoriesReady && vocabularyReady;
    }


}
