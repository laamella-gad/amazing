package com.laamella.amazingmazes.solvers;

import com.laamella.amazingmazes.mazemodel.MazeState;

public interface Solver {
    MazeState VISITED_WHILE_SOLVING = MazeState.singletonInstance();
    MazeState SOLUTION = MazeState.singletonInstance();
}
