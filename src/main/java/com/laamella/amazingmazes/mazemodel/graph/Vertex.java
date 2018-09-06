package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.State;

import java.util.Set;

/**
 *
 */
public interface Vertex extends State {
    Set<Edge> getEdges();

    Graph getGraph();
}
