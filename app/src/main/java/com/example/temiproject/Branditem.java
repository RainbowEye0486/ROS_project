package com.example.temiproject;

public class Branditem {

    private String Title;
    private int description;
    int insideGraph;
    int thumbnail;


    public Branditem() {
    }

    public Branditem(String title, int description, int thumbnail, int insideGraph) {
        Title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.insideGraph = insideGraph;
    }

    public String getTitle() {
        return Title;
    }

    public int getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
