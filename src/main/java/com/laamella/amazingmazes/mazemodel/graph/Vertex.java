package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.Markable;

import java.util.Set;

public interface Vertex extends Markable {
    Set<Edge> getEdges();

    Graph getGraph();
}
