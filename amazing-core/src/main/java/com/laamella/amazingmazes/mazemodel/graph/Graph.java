package com.laamella.amazingmazes.mazemodel.graph;

import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;

/**
 * The complete collection of vertices and edges that form the graph.
 */
public interface Graph {
	Set<Vertex> getVertices();

	Set<Edge> getEdges();

	public static class UtilityWrapper implements Graph {
		private final Graph delegateGraph;

		public UtilityWrapper(final Graph delegateGraph) {
			this.delegateGraph = delegateGraph;
		}

		public Vertex getEntrance() {
			return getVertexWithState(ENTRANCE);
		}

		public Vertex getExit() {
			return getVertexWithState(EXIT);
		}

		public Vertex getVertexWithState(final Object state) {
			for (final Vertex vertex : getVertices()) {
				if (vertex.hasState(state)) {
					return vertex;
				}
			}
			return null;
		}

		@Override
		public Set<Edge> getEdges() {
			return delegateGraph.getEdges();
		}

		@Override
		public Set<Vertex> getVertices() {
			return delegateGraph.getVertices();
		}
	}
}
