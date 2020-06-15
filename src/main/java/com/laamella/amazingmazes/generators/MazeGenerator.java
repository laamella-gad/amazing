package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.Marker;

/**
 * A maze generator takes a maze model, and renders a maze in it according to
 * some algorithm.
 */
public interface MazeGenerator {
    Marker VISITED_WHILE_GENERATING = Marker.singletonInstance();
    Marker POSSIBLE_EXIT = Marker.singletonInstance();
}
