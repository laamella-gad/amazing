package com.laamella.daedalousy.mazemodel;

public class MatrixSquareTileMap implements OrthogonalTileMap {

	private final int width;
	private final int height;
	private final boolean[][] matrix;

	public MatrixSquareTileMap(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.matrix = new boolean[width][height];
	}

	public boolean isMazeTileSolid(int x, int y) {
		return matrix[x][y];
	}

	public void setMazeTile(int x, int y, boolean solid) {
		matrix[x][y] = solid;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		String string = "";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isMazeTileSolid(x, y)) {
					string += "#";
				} else {
					string += " ";
				}
			}
			string += "\n";
		}
		return string;
	}
}
