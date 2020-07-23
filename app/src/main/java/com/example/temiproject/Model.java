package com.example.temiproject;

public class Model {

    private int photo;
    private int frame;

    public Model(int photo, int frame) {
        this.photo = photo;
        this.frame = frame;
    }

    public int getPhoto() {
        return photo;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
