package com.laamella.amazingmazes.operations;

import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;

import static com.laamella.amazingmazes.generators.MazeGenerator.*;
import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;
import static com.laamella.amazingmazes.operations.VertexDistanceMarker.*;

/**
 * Not a solver, but uses a solving algorithm. Finds the square on the border of
 * the grid that is the most distant from the entrance.
 */
public class MostDistantExitMarker {
    public void findMostDistantExit(Graph graph) {
        Vertex entrance = graph.getEntrance();
        new VertexDistanceMarker().mark(entrance);

        Vertex mostDistantExit = null;
        int largestDistance = 0;
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.isMarked(POSSIBLE_EXIT)) {
                int distance = vertex.getNumberMarker(DISTANCE);
                if (distance > largestDistance) {
                    largestDistance = distance;
                    mostDistantExit = vertex;
                }
            }
        }
        mostDistantExit.mark(EXIT);
    }
}
