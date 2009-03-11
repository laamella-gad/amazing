package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.grid.Grid;

public interface GridMazeGenerator extends MazeGenerator {
	void generateMaze(Grid grid);
}
