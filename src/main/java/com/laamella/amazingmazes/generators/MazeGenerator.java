package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.MazeState;

/**
 * A maze generator takes a maze model, and renders a maze in it according to
 * some algorithm.
 */
public interface MazeGenerator {
    MazeState VISITED_WHILE_GENERATING = MazeState.singletonInstance();
    MazeState POSSIBLE_EXIT = MazeState.singletonInstance();
}
