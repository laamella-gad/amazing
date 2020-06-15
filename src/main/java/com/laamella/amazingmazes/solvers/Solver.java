package com.laamella.amazingmazes.solvers;

import com.laamella.amazingmazes.mazemodel.Marker;

public interface Solver {
    Marker VISITED_WHILE_SOLVING = Marker.singletonInstance();
    Marker SOLUTION = Marker.singletonInstance();
}
