package com.laamella.amazingmazes.operations;

import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;

import java.util.HashSet;
import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker.DISTANCE_FROM_DEAD_END;
import static com.laamella.amazingmazes.solvers.Solver.SOLUTION;

/**
 * Attempt at using the distances to dead ends to find the longest path.
 * Algorithm doesn't work right.
 */
public class MostDistantEntranceAndExitFinder {

    public void execute(Graph graph) {
        Vertex midPoint = getVertexWithHighestDistance(graph.getVertices());
        midPoint.setState(SOLUTION, true);
        Set<Vertex> verticesAroundVertex = getVerticesAroundVertex(midPoint);
        Vertex direction1 = getVertexWithHighestDistance(verticesAroundVertex);
        verticesAroundVertex.remove(direction1);
        Vertex direction2 = getVertexWithHighestDistance(verticesAroundVertex);
        walkToDeadEndAndMark(direction1, ENTRANCE);
        walkToDeadEndAndMark(direction2, EXIT);

    }

    private void walkToDeadEndAndMark(Vertex currentVertex, MazeDefinitionState marker) {
        currentVertex.setState(SOLUTION, true);
        Vertex nextVertex = getVertexWithHighestDistance(getVerticesAroundVertex(currentVertex));
        if (nextVertex == null) {
            // Reached the end of the walk
            currentVertex.setState(marker, true);
            return;
        }
        walkToDeadEndAndMark(nextVertex, marker);
    }

    private Vertex getVertexWithHighestDistance(Set<Vertex> vertices) {
        Vertex bestVertex = null;
        for (Vertex vertex : vertices) {
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
        Set<Vertex> vertices = new HashSet<>();
        for (Edge edge : vertex.getEdges()) {
            if (edge.hasState(PASSAGE)) {
                vertices.add(edge.travel(vertex));
            }
        }
        return vertices;
    }
}
