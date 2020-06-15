package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.Markable;

/**
 * Connects two vertices.
 */
// TODO edges might need an orientation in degrees
// TODO edges can be made uni-directional
public interface Edge extends Markable {
    Vertex getVertexA();

    Vertex getVertexB();

    Vertex travel(Vertex sourceVertex);

    Graph getGraph();
}
