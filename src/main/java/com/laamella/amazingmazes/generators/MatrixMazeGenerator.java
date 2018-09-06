package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

/**
 * A maze generator that works directly on matrices. A matrix contains only
 * squares, which can be a passage or not. Two squares are connected when they
 * are next to each other and passages. Since the connections are not static, a
 * matrix cannot be represented as a static graph.
 */
public interface MatrixMazeGenerator extends MazeGenerator {
    void generateMaze(StateMatrix matrix);
}
