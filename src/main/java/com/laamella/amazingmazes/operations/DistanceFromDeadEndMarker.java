package com.laamella.amazingmazes.operations;

import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import com.laamella.amazingmazes.observe.Observable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Marks every vertex with the distance to the farthest dead end.
 * <p>
 * The algorithm needs at least one dead end to function.
 * <p>
 * The algorithm will fail when the graph has loops.
 */
public class DistanceFromDeadEndMarker extends Observable {
    public static final Object DISTANCE_FROM_DEAD_END = new Object();

    public static class Result {
        public final Set<Vertex> unmarkedVertices;
        public final boolean success;

        private Result(Set<Vertex> unmarkedVertices) {
            this.unmarkedVertices = unmarkedVertices;
            this.success = unmarkedVertices.size() == 0;
        }

    }

    public Result execute(final Graph graph) {
        final Set<Vertex> unmarkedVertices = new LinkedHashSet<>(graph.getVertices());

        do {
            notifyObservers();
            final Set<Vertex> newlyMarkedVertices = new LinkedHashSet<>();
            for (final Vertex currentVertex : unmarkedVertices) {
                if (countUnmarkedExits(currentVertex) < 2) {
                    currentVertex.setState(DISTANCE_FROM_DEAD_END, oneHigherThanVerticesAroundMe(currentVertex));
                    newlyMarkedVertices.add(currentVertex);
                    setChanged();
                }
            }
            unmarkedVertices.removeAll(newlyMarkedVertices);

            if (!hasChanged()) {
                return new Result(unmarkedVertices);
            }
        } while (true);
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
