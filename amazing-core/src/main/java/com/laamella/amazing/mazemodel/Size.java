package com.laamella.amazing.mazemodel;

/**
 * A simple 2D size indicator.
 */
public class Size {
	public final int width;
	public final int height;
	public final int area;

	public Size(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.area = width * height;
	}
}
