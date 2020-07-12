package com.easy2learn.game.ui.topic;

public class Category {

    public int id;
    public String name;
    public String coverUrl;

    public Category() {}

    public Category(int id, String name, String coverUrl) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCoverUrl() {
        return coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
