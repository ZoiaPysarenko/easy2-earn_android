package com.easy2learn.game.ui.topic.games;

public class MemoryItem {

    private int id;
    private boolean isImg;
    private String itemValue;
    private boolean isEnabled = true;

    public MemoryItem(int id, boolean isImg, String itemValue) {
        this.id = id;
        this.isImg = isImg;
        this.itemValue = itemValue;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isImg() {
        return isImg;
    }
    public void setImg(boolean img) {
        isImg = img;
    }
    public String getItemValue() {
        return itemValue;
    }
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    @Override
    public String toString() {
        return "MemoryItem{" +
                "id=" + id +
                ", isImg=" + isImg +
                ", itemValue='" + itemValue + '\'' +
                '}';
    }
}
