package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.Marker;

import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.ENTRANCE;
import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.EXIT;

/**
 * The complete collection of vertices and edges that form the graph.
 */
public interface Graph {
    Set<Vertex> getVertices();

    Set<Edge> getEdges();

    default Vertex getEntrance() {
        return getVertexWithMarker(ENTRANCE);
    }

    default Vertex getExit() {
        return getVertexWithMarker(EXIT);
    }

    default Vertex getVertexWithMarker(Marker marker) {
        for (Vertex vertex : getVertices()) {
            if (vertex.isMarked(marker)) {
                return vertex;
            }
        }
        return null;
    }
}
