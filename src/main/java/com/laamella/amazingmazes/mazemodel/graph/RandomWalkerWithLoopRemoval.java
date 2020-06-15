package com.laamella.amazingmazes.mazemodel.graph;

import com.laamella.amazingmazes.generators.Randomizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Does a random walk through a graph. If it walks on a vertex it has walked on
 * before, the part of the walk inbetween is removed, thereby removing the loop.
 */
public abstract class RandomWalkerWithLoopRemoval {
    public static class Step {
        public enum Direction {
            A_TO_B, B_TO_A
        }

        public final Edge edge;
        public final Direction direction;
        public final Vertex from;
        public final Vertex to;

        public Step(Vertex from, Vertex to, Edge edge) {
            this.from = from;
            this.to = to;
            this.edge = edge;
            if (edge.getVertexA().equals(from)) {
                this.direction = Direction.A_TO_B;
            } else {
                this.direction = Direction.B_TO_A;
            }
        }
    }

    private final Randomizer randomizer;

    public RandomWalkerWithLoopRemoval(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    public List<Step> walk(Vertex startVertex) {
        List<Step> steps = new ArrayList<>();
        Vertex currentVertex = startVertex;
        do {
            Set<Edge> walkableEdges = new HashSet<>();
            for (Edge edge : currentVertex.getEdges()) {
                if (isWalkable(edge)) {
                    walkableEdges.add(edge);
                }
            }
            Edge randomEdge = randomizer.pickOne(walkableEdges);
            Vertex nextVertex = randomEdge.travel(currentVertex);
            steps = removeLoopWhenCreatingOne(steps, currentVertex);
            Step step = new Step(currentVertex, nextVertex, randomEdge);
            steps.add(step);
            currentVertex = nextVertex;
        } while (!endCondition(currentVertex));
        return steps;
    }

    private List<Step> removeLoopWhenCreatingOne(List<Step> steps, Vertex vertex) {
        List<Step> looplessSteps = new ArrayList<>();
        for (Step step : steps) {
            if (step.from == vertex) {
                return looplessSteps;
            }
            looplessSteps.add(step);
        }
        return looplessSteps;
    }

    protected abstract boolean endCondition(Vertex currentVertex);

    protected abstract boolean isWalkable(Edge edge);
}
