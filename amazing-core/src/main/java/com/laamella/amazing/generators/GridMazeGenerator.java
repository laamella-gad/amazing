package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.grid.Grid;

public interface GridMazeGenerator extends MazeGenerator {
	void generateMaze(Grid grid);
}
