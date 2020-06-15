package com.laamella.amazingmazes.mazemodel;

/**
 * A simple 2D size indicator.
 */
public class Size {
    public final int width;
    public final int height;
    public final int area;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
        this.area = width * height;
    }
}

