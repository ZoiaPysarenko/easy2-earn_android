package com.easy2learn.game.ui.topic;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class VocabularyItem implements Parcelable {

    public int category;
    public int id;
    public String name;
    public String url;
    public String word;
    public List<String> quiz = new ArrayList<>();

    public VocabularyItem(){

    }


    public VocabularyItem(int category, int id, String name, String url, String word, List<String> quiz) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.url = url;
        this.word = word;
        this.quiz = quiz;
    }

    protected VocabularyItem(Parcel in) {
        category = in.readInt();
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        word = in.readString();
        in.readStringList(this.quiz);
    }

    public static final Creator<VocabularyItem> CREATOR = new Creator<VocabularyItem>() {
        @Override
        public VocabularyItem createFromParcel(Parcel in) {
            return new VocabularyItem(in);
        }

        @Override
        public VocabularyItem[] newArray(int size) {
            return new VocabularyItem[size];
        }
    };

    public void setCategory(int category) {
        this.category = category;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getCategory() {
        return category;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public String getWord() {
        return word;
    }
    public List<String> getQuiz() {
        return quiz;
    }
    public void setQuiz(List<String> quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "VocabularyItem{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", word='" + word + '\'' +
                ", quiz=" + quiz +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(word);
        dest.writeStringList(quiz);
    }
}

