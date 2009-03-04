package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.grid.RowGenerator;

/**
 * Generates a grid maze row by row.
 */
public interface RowMazeGenerator extends MazeGenerator {
	void generateMaze(RowGenerator row);
}
