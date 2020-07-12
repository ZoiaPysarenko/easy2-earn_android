package com.easy2learn.game.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.easy2learn.game.FirebaseDAO;
import com.easy2learn.game.FirebaseStorageUtility;
import com.easy2learn.game.R;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)

public class TopicsViewAdapter extends RecyclerView.Adapter<TopicsViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Category> categories;


    public TopicsViewAdapter(Context mContext, ArrayList<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_item_topic, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_topic_title.setText(categories.get(position).getName());
        //holder.img_topic_thumbnail.setImageResource(categories.get(position).getCoverUrl());
        FirebaseStorageUtility.getImage(holder.img_topic_thumbnail, categories.get(position).getId(), categories.get(position).getCoverUrl());

        //Set click listener
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TopicActivity.class);

            int category = categories.get(position).getId();
            intent.putParcelableArrayListExtra("Topic data", FirebaseDAO.getDatabase().getVocabulariesByCategoryId(category));
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_topic_title;
        ImageView img_topic_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_topic_title = (TextView) itemView.findViewById(R.id.topic_title_id);
            img_topic_thumbnail = (ImageView) itemView.findViewById(R.id.category_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }

    }












}
