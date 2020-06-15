package com.laamella.amazingmazes.mazemodel;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface MazeState {
    static MazeState singletonInstance() {
        return new MazeState() {
        };
    }
}
