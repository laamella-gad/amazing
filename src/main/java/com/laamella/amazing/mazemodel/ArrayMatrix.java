package com.laamella.amazing.mazemodel;

public class ArrayMatrix implements Matrix {
	private final int[][] matrix;
	private final Size size;

	public ArrayMatrix(Size size) {
		this.size=size;
		this.matrix = new int[size.width][size.height];
	}

	public int get(Position position) {
		return matrix[position.x][position.y];
	}

	public Size getSize() {
		return size;
	}

	public void set(Position position, int value) {
		matrix[position.x][position.y] = value;
	}

}
