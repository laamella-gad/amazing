package com.laamella.amazingmazes.mazemodel.graph;

import java.util.Set;

import com.laamella.amazingmazes.mazemodel.State;

/**
 * 
 */
public interface Vertex extends State {
	Set<Edge> getEdges();

	Graph getGraph();
}
