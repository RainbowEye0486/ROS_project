package com.example.temiproject;

import android.widget.ImageView;

public class mapView {
    String floor;
    ImageView overlap;
    int order;

    public mapView(String floor, ImageView overlap, int order) {
        this.floor = floor;
        this.overlap = overlap;
        this.order = order;
    }

    public String getFloor() {
        return floor;
    }

    public ImageView getOverlap() {
        return overlap;
    }

    public int getOrder() {
        return order;
    }

    public ImageView getBitmap(){
        return overlap;
    }
}
