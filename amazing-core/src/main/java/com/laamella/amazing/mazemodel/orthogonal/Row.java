package com.laamella.amazing.mazemodel.orthogonal;

/**
 * Interface to receive data from progressive maze algorithms. Receives one row
 * at a time, and is called back to ask whether the algorithm is done.
 */
public interface Row {
	void nextRow();

	Square getSquare(int x);

	int getWidthInSquares();

	int getWidthInWalls();

	boolean finishAlgorithm();
}
