package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.orthogonal.Grid;

public interface GridMazeGenerator extends MazeGenerator {
	void generateMaze(Grid grid);
}
