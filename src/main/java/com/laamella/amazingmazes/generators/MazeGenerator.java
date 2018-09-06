package com.laamella.amazingmazes.generators;

/**
 * A maze generator takes a maze model, and renders a maze in it according to
 * some algorithm.
 */
public interface MazeGenerator {
    Object VISITED_WHILE_GENERATING = new Object();
    Object POSSIBLE_EXIT = new Object();
}
