package com.laamella.amazing.mazemodel.graph;

import java.util.Set;

/**
 * The complete collection of vertices and edges that form the graph.
 */
public interface Graph {
	Set<Vertex> getVertices();

	Set<Edge> getEdges();
}
