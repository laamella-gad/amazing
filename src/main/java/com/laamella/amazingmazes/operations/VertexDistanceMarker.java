package com.laamella.amazingmazes.operations;

import com.laamella.amazingmazes.mazemodel.Marker;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;

/**
 * This will mark every vertex in the graph with its distance from the start
 * vertex.
 */
public class VertexDistanceMarker {
    public static final Marker DISTANCE = Marker.singletonInstance();

    public void mark(Vertex startVertex) {
        recurse(startVertex, 0);
    }

    private void recurse(Vertex vertex, int distance) {
        if (vertex.isMarked(DISTANCE)) {
            int existingDistance = vertex.getNumberMarker(DISTANCE);
            if (existingDistance < distance) {
                // Found a better path already
                return;
            }
        }
        vertex.markNumber(DISTANCE, distance);
        for (Edge edge : vertex.getEdges()) {
            if (edge.isMarked(PASSAGE)) {
                Vertex nextVertex = edge.travel(vertex);
                recurse(nextVertex, distance + 1);
            }
        }
    }
}
