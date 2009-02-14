package com.laamella.daedalousy.mazemodel.orthogonal;

public interface Grid {

	Square getSquare(int x, int y);

	int getWidthInSquares();

	int getWidthInWalls();

	int getHeightInSquares();

	int getHeightInWalls();
}
