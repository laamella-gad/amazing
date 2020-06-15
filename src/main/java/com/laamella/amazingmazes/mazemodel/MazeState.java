package com.laamella.amazingmazes.mazemodel;

public interface MazeState {
    static MazeState singletonInstance() {
        return new MazeState() {
        };
    }
}
