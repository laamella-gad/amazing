package com.laamella.daedalousy.mazemodel;

import com.laamella.daedalousy.mazemodel.orthogonal.GridManipulator;

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

	@Override
	public String toString() {
		final StringBuffer maze = new StringBuffer();
		GridManipulator.visit2dArray(matrix, new GridManipulator.Visitor2dArray() {
			public void visit(int x, int y) {
				maze.append(toChar(matrix[x][y]));
			}

			public void newRow() {
				maze.append("\n");
			}
		});
		return maze.toString();
	}

	private char toChar(int value) {
		switch (value) {
		case 0:
			return ' ';
		case 1:
			return '#';
		default:
			return (char) (value % 26 + 'A');
		}
	}
}
