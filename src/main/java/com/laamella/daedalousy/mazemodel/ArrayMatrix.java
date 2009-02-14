package com.laamella.daedalousy.mazemodel;

public class ArrayMatrix implements Matrix {
	private final int width;
	private final int height;
	private final int[][] matrix;

	public ArrayMatrix(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new int[width][height];
	}

	public int get(int x, int y) {
		return matrix[x][y];
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void set(int x, int y, int value) {
		matrix[x][y] = value;
	}

}
