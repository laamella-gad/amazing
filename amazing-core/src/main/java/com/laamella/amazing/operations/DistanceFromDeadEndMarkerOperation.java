package com.laamella.amazing.operations;

import java.util.*;

import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.graph.*;

/**
 * Finds the longest path in a graph, then adds the entrance and exit at the
 * ends of it.
 * <p>
 * The algorithm needs at least one dead end to function.
 */
public class DistanceFromDeadEndMarkerOperation extends Observable {
	public static final Object DISTANCE_FROM_DEAD_END = new Object();

	public void go(final Graph graph) {

		// Put a distance of 0 in all dead ends
		final Set<Vertex> nonDeadEnds = new LinkedHashSet<Vertex>();
		for (final Vertex vertex : graph.getVertices()) {
			if (isDeadEnd(vertex)) {
				vertex.setState(DISTANCE_FROM_DEAD_END, 0);
			} else {
				nonDeadEnds.add(vertex);
			}
		}
		setChanged();

		do {
			notifyObservers();
			for (final Vertex vertex : nonDeadEnds) {
				for (final Edge edge : vertex.getEdges()) {
					final Integer myDistance = vertex.getState(DISTANCE_FROM_DEAD_END);
					final Vertex otherVertex = edge.travel(vertex);
					final Integer otherDistance = otherVertex.getState(DISTANCE_FROM_DEAD_END);
					if (otherDistance != null) {
						if (myDistance == null || otherDistance > myDistance) {
							vertex.setState(DISTANCE_FROM_DEAD_END, otherDistance + 1);
							setChanged();
						}
					}
				}
			}
		} while (hasChanged());
	}

	private boolean isDeadEnd(Vertex vertex) {
		int exits = 0;
		for (final Edge edge : vertex.getEdges()) {
			if (edge.hasState(MazeDefinitionState.PASSAGE)) {
				exits++;
				if (exits > 1) {
					return false;
				}
			}
		}
		return true;
	}
}
