package com.easy2learn.game.ui.topic.games;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.easy2learn.game.FirebaseStorageUtility;
import com.easy2learn.game.R;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MemoryGameAdapter extends BaseAdapter {

    private final Context mContext;
    private ArrayList<MemoryItem> memoryItems;
    private int curCategory;

    public MemoryGameAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public MemoryGameAdapter(Context mContext, int curCategory) {
        this.mContext = mContext;
        this.curCategory = curCategory;
    }

    public MemoryGameAdapter(Context mContext, ArrayList<MemoryItem> memoryItems) {
        this.mContext = mContext;
        this.memoryItems = memoryItems;
    }


    public void setMemoryItems(ArrayList<MemoryItem> memoryItems) {
        this.memoryItems = memoryItems;
    }

    @Override
    public int getCount() {
        return memoryItems.size();
    }

    @Override
    public MemoryItem getItem(int position) {
        return memoryItems.get(position);

    }

    @Override
    public long getItemId(int position) {
        return memoryItems.get(position).getId();
    }


    @Override
    public boolean isEnabled(int position) {
        return memoryItems.get(position).isEnabled();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


       //add data to the view
        MemoryItem currentItem = getItem(position);

        if (currentItem.isImg()){

            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.memory_item_img, null);
            }


            final ImageView imageView = convertView.findViewById(R.id.memoryImageView);
            final ImageView imageViewCover = convertView.findViewById(R.id.memoryImageViewCover);
            String imgUrl = currentItem.getItemValue();
            String coverUrl = "images/general/question.jpg";
//            imageView.setImageResource(imgUrl);
//            imageViewCover.setImageResource(R.drawable.question);
            FirebaseStorageUtility.getImage(imageView, curCategory, imgUrl);
            FirebaseStorageUtility.getImageByPath(imageViewCover, coverUrl);

            return convertView;
        }else{

            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.memory_item_text, null);
            }

            final TextView textView = convertView.findViewById(R.id.memoryTextView);
            final ImageView imageViewCover = convertView.findViewById(R.id.memoryImageViewCover);
            textView.setText(currentItem.getItemValue());
            imageViewCover.setImageResource(R.drawable.question);


            return convertView;
        }



    }

}
