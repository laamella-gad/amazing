package com.laamella.amazing.operations;

import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.graph.Graph;
import com.laamella.amazing.mazemodel.graph.Vertex;

/**
 * Not a solver, but uses a solving algorithm. Finds the square on the border of
 * the grid that is the most distant from the entrance.
 */
public class MostDistantExitMarkOperation {
	public void findMostDistantExit(final Graph graph) {
		final Graph.UtilityWrapper utilityGraph = new Graph.UtilityWrapper(graph);
		final Vertex entrance = utilityGraph.getEntrance();
		new VertexDistanceMarkingOperation().mark(entrance);

		Vertex mostDistantExit = null;
		int largestDistance = 0;
		for (final Vertex vertex : graph.getVertices()) {
			if (vertex.hasState(MazeGenerator.POSSIBLE_EXIT)) {
				final int distance = vertex.getState(VertexDistanceMarkingOperation.DISTANCE);
				if (distance > largestDistance) {
					largestDistance = distance;
					mostDistantExit = vertex;
				}
			}
		}
		mostDistantExit.setState(MazeDefinitionState.EXIT, true);
	}
}
