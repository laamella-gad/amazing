package com.laamella.amazing.mazemodel.graph;

import com.laamella.amazing.mazemodel.State;

/**
 * Connects two vertices.
 */
public interface Edge extends State {
	Vertex getVertexA();

	Vertex getVertexB();

	Vertex travel(Vertex sourceVertex);

	Graph getGraph();
}
