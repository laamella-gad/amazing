package com.laamella.amazingmazes.operations;

import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;

/**
 * This will mark every vertex in the graph with its distance from the start
 * vertex.
 */
public class VertexDistanceMarker {
    public static final MazeState DISTANCE = new MazeState(){};

    public void mark(final Vertex startVertex) {
        recurse(startVertex, 0);
    }

    private void recurse(final Vertex vertex, final int distance) {
        if (vertex.hasState(DISTANCE)) {
            final int existingDistance = vertex.getState(DISTANCE);
            if (existingDistance < distance) {
                // Found a better path already
                return;
            }
        }
        vertex.setState(DISTANCE, distance);
        for (final Edge edge : vertex.getEdges()) {
            if (edge.hasState(MazeDefinitionState.PASSAGE)) {
                final Vertex nextVertex = edge.travel(vertex);
                recurse(nextVertex, distance + 1);
            }
        }
    }
}
