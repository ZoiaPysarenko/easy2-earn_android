package com.easy2learn.game;

public class IntroScreenItem {

    private String title;
    private String description;
    private int screenImg;

    public IntroScreenItem(String title, String description, int screenImg) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getScreenImg() {
        return screenImg;
    }
    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }
}
