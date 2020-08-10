package com.example.temiproject;

public class Branditem {

    private String Title;
    private String description;

    int thumbnail;


    public Branditem() {
    }

    public Branditem(String title, String description, int thumbnail) {
        Title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
