package com.example.lab07;

public class Publisher {
    String name;
    int imageResId; // Resource ID for the image

    public Publisher(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
}
