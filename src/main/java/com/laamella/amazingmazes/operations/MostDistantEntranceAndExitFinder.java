package com.laamella.amazingmazes.operations;

import java.util.HashSet;
import java.util.Set;


import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.graph.*;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker.*;
import static com.laamella.amazingmazes.solvers.Solver.*;

/**
 * Attempt at using the distances to dead ends to find the longest path.
 * Algorithm doesn't work right.
 */
public class MostDistantEntranceAndExitFinder {

	public void execute(final Graph graph) {
		final Vertex midPoint = getVertexWithHighestDistance(graph.getVertices());
		midPoint.setState(SOLUTION, true);
		final Set<Vertex> verticesAroundVertex = getVerticesAroundVertex(midPoint);
		final Vertex direction1 = getVertexWithHighestDistance(verticesAroundVertex);
		verticesAroundVertex.remove(direction1);
		final Vertex direction2 = getVertexWithHighestDistance(verticesAroundVertex);
		walkToDeadEndAndMark(direction1, ENTRANCE);
		walkToDeadEndAndMark(direction2, EXIT);

	}

	private void walkToDeadEndAndMark(final Vertex currentVertex, final MazeDefinitionState marker) {
		currentVertex.setState(SOLUTION, true);
		final Vertex nextVertex = getVertexWithHighestDistance(getVerticesAroundVertex(currentVertex));
		if (nextVertex == null) {
			// Reached the end of the walk
			currentVertex.setState(marker, true);
			return;
		}
		walkToDeadEndAndMark(nextVertex, marker);
	}

	private Vertex getVertexWithHighestDistance(final Set<Vertex> vertices) {
		Vertex bestVertex = null;
		for (final Vertex vertex : vertices) {
			if (!vertex.hasState(SOLUTION)) {
				if (bestVertex == null) {
					bestVertex = vertex;
				} else if (vertex.getState(DISTANCE_FROM_DEAD_END) > bestVertex.getState(DISTANCE_FROM_DEAD_END)) {
					bestVertex = vertex;
				}
			}
		}
		return bestVertex;
	}

	private Set<Vertex> getVerticesAroundVertex(Vertex vertex) {
		final Set<Vertex> vertices = new HashSet<Vertex>();
		for (final Edge edge : vertex.getEdges()) {
			if (edge.hasState(PASSAGE)) {
				vertices.add(edge.travel(vertex));
			}
		}
		return vertices;
	}
}
