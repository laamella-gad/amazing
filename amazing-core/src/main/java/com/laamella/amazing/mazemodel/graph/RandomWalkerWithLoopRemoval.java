package com.laamella.amazing.mazemodel.graph;

import java.util.*;

import com.laamella.amazing.generators.Randomizer;

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

		public Step(final Vertex from, final Vertex to, final Edge edge) {
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

	public RandomWalkerWithLoopRemoval(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	public List<Step> walk(final Vertex startVertex) {
		List<Step> steps = new ArrayList<Step>();
		Vertex currentVertex = startVertex;
		do {
			final Set<Edge> walkableEdges = new HashSet<Edge>();
			for (final Edge edge : currentVertex.getEdges()) {
				if (isWalkable(edge)) {
					walkableEdges.add(edge);
				}
			}
			final Edge randomEdge = randomizer.pickOne(walkableEdges);
			final Vertex nextVertex = randomEdge.travel(currentVertex);
			steps = removeLoopWhenCreatingOne(steps, currentVertex);
			final Step step = new Step(currentVertex, nextVertex, randomEdge);
			steps.add(step);
			currentVertex = nextVertex;
		} while (!endCondition(currentVertex));
		return steps;
	}

	private List<Step> removeLoopWhenCreatingOne(final List<Step> steps, final Vertex vertex) {
		List<Step> looplessSteps = new ArrayList<Step>();
		for (final Step step : steps) {
			if (step.from == vertex) {
				return looplessSteps;
			}
			looplessSteps.add(step);
		}
		return looplessSteps;
	}

	protected abstract boolean endCondition(final Vertex currentVertex);

	protected abstract boolean isWalkable(Edge edge);

}
