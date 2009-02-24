package com.laamella.amazing.mazemodel.graph;

import java.util.Set;

import com.laamella.amazing.mazemodel.State;

/**
 * 
 */
public interface Vertex extends State {
	Set<Edge> getEdges();

	Graph getGraph();
}
