package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.grid.RowGenerator;

/**
 * Generates a grid maze row by row.
 */
public interface RowMazeGenerator extends MazeGenerator {
    void generateMaze(RowGenerator row);
}
