package com.laamella.amazing.operations;

import java.util.*;

import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.graph.*;

/**
 * Finds the longest path in a graph, then adds the entrance and exit at the
 * ends of it.
 * <p>
 * The algorithm needs at least one dead end to function.
 * <p>
 * The algorithm will fail when the graph has loops.
 */
public class DistanceFromDeadEndMarkerOperation extends Observable {
	public static final Object DISTANCE_FROM_DEAD_END = new Object();

	public void go(final Graph graph) {
		final Set<Vertex> unmarkedVertices = new LinkedHashSet<Vertex>(graph.getVertices());

		do {
			notifyObservers();
			final Set<Vertex> newlyMarkedVertices = new LinkedHashSet<Vertex>();
			for (final Vertex currentVertex : unmarkedVertices) {
				if (countUnmarkedExits(currentVertex) <2) {
					currentVertex.setState(DISTANCE_FROM_DEAD_END, oneHigherThanVerticesAroundMe(currentVertex));
					newlyMarkedVertices.add(currentVertex);
					setChanged();
				}
			}
			unmarkedVertices.removeAll(newlyMarkedVertices);
		} while (hasChanged());
	}

	private int oneHigherThanVerticesAroundMe(Vertex vertex) {
		int oneHigher = 0;
		for (final Edge edge : vertex.getEdges()) {
			if (edge.hasState(MazeDefinitionState.PASSAGE)) {
				final Vertex otherVertex = edge.travel(vertex);
				final Integer distance = otherVertex.getState(DISTANCE_FROM_DEAD_END);
				if (distance != null) {
					if (oneHigher < distance + 1) {
						oneHigher = distance + 1;
					}
				}
			}
		}
		return oneHigher;
	}

	private int countUnmarkedExits(final Vertex vertex) {
		int unmarkedExits = 0;
		for (final Edge edge : vertex.getEdges()) {
			if (edge.hasState(MazeDefinitionState.PASSAGE)) {
				final Vertex otherVertex = edge.travel(vertex);
				if (!otherVertex.hasState(DISTANCE_FROM_DEAD_END)) {
					unmarkedExits++;
				}
			}
		}
		return unmarkedExits;
	}
}
