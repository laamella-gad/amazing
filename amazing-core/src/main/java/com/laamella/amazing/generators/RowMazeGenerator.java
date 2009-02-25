package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.orthogonal.Row;

/**
 * Generates a grid maze row by row.
 */
public interface RowMazeGenerator extends MazeGenerator {
	void generateMaze(Row row);
}
