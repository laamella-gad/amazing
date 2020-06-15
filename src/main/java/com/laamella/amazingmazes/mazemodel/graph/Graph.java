package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.mazemodel.MazeState;

import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.ENTRANCE;
import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.EXIT;

/**
 * The complete collection of vertices and edges that form the graph.
 */
public interface Graph {
    Set<Vertex> getVertices();

    Set<Edge> getEdges();

    default Vertex getEntrance() {
        return getVertexWithState(ENTRANCE);
    }

    default Vertex getExit() {
        return getVertexWithState(EXIT);
    }

    default Vertex getVertexWithState(MazeState state) {
        for (Vertex vertex : getVertices()) {
            if (vertex.hasState(state)) {
                return vertex;
            }
        }
        return null;
    }
}
