package com.easy2learn.game;

import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

@RequiresApi(api = Build.VERSION_CODES.N)

public class FirebaseStorageUtility {

    final static String STORAGE_BUCKET_NAME = "gs://easy-2-learn-783d5";

    private static StorageReference mStorageRef;

    private static FirebaseStorageUtility shared = null;
//    public static FirebaseStorageUtility getInstance(){
//        //lazy loaded singleton
//        if (shared == null){
//            shared = new FirebaseStorageUtility();
//            //mStorageRef = FirebaseStorage.getInstance().getReference();
//        }
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//        return shared;
//    }

    public static void Init(){
        try{
            mStorageRef = FirebaseStorage.getInstance().getReference();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void getImage(ImageView imgView, int category, String imgUrl){
        String path =  "images/" + category + "/" + imgUrl + ".jpg";
        StorageReference curImageRef = mStorageRef.child(path);
        //StorageReference curImageRef2 = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/easy-2-learn-783d5.appspot.com/o/images%2F1%2Ffruits.jpg");

        curImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get()
                        .load(uri)
                        .into(imgView);

            }
        });

    }

    public static void getImageByPath(ImageView imgView, String path){
        StorageReference curImageRef = mStorageRef.child(path);
        curImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get()
                        .load(uri)
                        .into(imgView);

            }
        });
    }
}

