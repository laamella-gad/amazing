package com.laamella.amazingmazes.generators.daedalus;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;

import com.laamella.amazingmazes.generators.GraphMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.graph.*;

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
 * @see com.laamella.amazingmazes.solvers.RecursiveBacktrackerSolver <p>
 *      <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 *      description</a>
 */
public class RecursiveBacktrackerMazeGenerator implements GraphMazeGenerator {
	private final Randomizer randomizer;

	public RecursiveBacktrackerMazeGenerator(final Randomizer randomGenerator) {
		this.randomizer = randomGenerator;
	}

	@Override
	public void generateMaze(final Graph graph) {
		final Vertex entranceVertex = new Graph.UtilityWrapper(graph).getEntrance();
		recurse(entranceVertex);
	}

	private void recurse(final Vertex currentVertex) {
		currentVertex.setState(VISITED_WHILE_GENERATING, true);
		for (final Edge edge : randomizer.shuffle(currentVertex.getEdges())) {
			final Vertex otherVertex = edge.travel(currentVertex);
			if (!otherVertex.hasState(VISITED_WHILE_GENERATING)) {
				edge.setState(PASSAGE, true);
				recurse(otherVertex);
			}
		}
	}
}
