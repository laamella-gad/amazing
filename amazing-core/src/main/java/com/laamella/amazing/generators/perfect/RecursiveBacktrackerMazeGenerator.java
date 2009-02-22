package com.laamella.amazing.generators.perfect;

import static com.laamella.amazing.generators.GeneratorStates.*;
import static com.laamella.amazing.mazemodel.MazeDefinitionStates.*;
import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.graph.Edge;
import com.laamella.amazing.mazemodel.graph.Vertex;

/**
 * <p>
 * This is somewhat related to the recursive backtracker solving method
 * described below, and requires stack up to the size of the Maze. When carving,
 * be as greedy as possible, and always carve into an unmade section if one is
 * next to the current cell. Each time you move to a new cell, push the former
 * cell on the stack. If there are no unmade cells next to the current position,
 * pop the stack to the previous position. The Maze is done when you pop
 * everything off the stack. This algorithm results in Mazes with about as high
 * a "river" factor as possible, with fewer but longer dead ends, and usually a
 * very long and twisty solution. It runs quite fast, although Prim's algorithm
 * is a bit faster. Recursive backtracking doesn't work as a wall adder, because
 * doing so tends to result in a solution path that follows the outside edge,
 * where the entire interior of the Maze is attached to the boundary by a single
 * stem.
 * 
 * @see com.laamella.amazing.solvers.RecursiveBacktrackerSolver
 * 
 */
public class RecursiveBacktrackerMazeGenerator implements MazeGenerator {
	private final Vertex startVertex;
	private final Randomizer randomizer;

	public RecursiveBacktrackerMazeGenerator(final Vertex startVertex, final Randomizer randomGenerator) {
		this.startVertex = startVertex;
		this.randomizer = randomGenerator;
		startVertex.setState(ENTRANCE, true);
		// TODO generate an exit
	}

	public void generateMaze() {
		recurse(startVertex);
	}

	private void recurse(final Vertex currentVertex) {
		currentVertex.setState(VISITED, true);
		for (final Edge edge : randomizer.randomizeCollection(currentVertex.getEdges())) {
			final Vertex otherVertex = edge.travel(currentVertex);
			if (!otherVertex.hasState(VISITED)) {
				edge.setState(OPEN, true);
				recurse(otherVertex);
			}
		}
	}
}
