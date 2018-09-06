package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.graph.Graph;

/**
 * A GraphMazeGenerator generates a maze in a graph.
 */
public interface GraphMazeGenerator extends MazeGenerator {
    /**
     * Generate a maze.
     */
    void generateMaze(Graph graph);
}
