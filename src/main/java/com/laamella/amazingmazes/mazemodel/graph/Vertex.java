package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.Stateful;

import java.util.Set;

public interface Vertex extends Stateful {
    Set<Edge> getEdges();

    Graph getGraph();
}
