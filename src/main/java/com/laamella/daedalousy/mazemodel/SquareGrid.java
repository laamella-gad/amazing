package com.laamella.daedalousy.mazemodel;

public interface SquareGrid {
	public static enum Wall {
		NORTH, SOUTH, EAST, WEST
	}

	void setMazeWall(int x, int y, Wall wall, boolean solid);

	boolean isMazeWallSolid(int x, int y, int wall);

	int getWidthInSquares();

	int getWidthInWalls();

	int getHeightInSquares();

	int getHeightInWalls();
}
