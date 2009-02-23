package com.laamella.amazing.solvers;

import com.laamella.amazing.mazemodel.State;
import com.laamella.amazing.mazemodel.graph.Edge;
import com.laamella.amazing.mazemodel.graph.Vertex;

/**
 * This will find a solution, but it won't necessarily find the shortest
 * solution. It focuses on you, is fast for all types of Mazes, and uses stack
 * space up to the size of the Maze. Very simple: If you're at a wall (or an
 * area you've already plotted), return failure, else if you're at the finish,
 * return success, else recursively try moving in the four directions. Plot a
 * line when you try a new direction, and erase a line when you return failure,
 * and a single solution will be marked out when you hit success. When
 * backtracking, it's best to mark the space with a special visited value, so
 * you don't visit it again from a different direction. In Computer Science
 * terms this is basically a depth first search. This method will always find a
 * solution if one exists, but it won't necessarily be the shortest solution.
 */
public class RecursiveBacktrackerSolver implements Solver {
	public boolean solve(final Vertex entrance) {
		return recurse(entrance);
	}

	private boolean recurse(final Vertex currentVertex) {
		currentVertex.setState(SolutionState.VISITED, true);

		if (currentVertex.hasState(State.MazeDefinitionState.EXIT)) {
			// Found the exit!
			currentVertex.setState(SolutionState.SOLUTION, true);
			return true;
		}

		for (final Edge edge : currentVertex.getEdges()) {
			final Vertex otherVertex = edge.travel(currentVertex);
			if (!otherVertex.hasState(SolutionState.VISITED)) {
				if (recurse(otherVertex)) {
					// The exit has been found ahead of us, so we're part of the
					// solution.
					edge.setState(SolutionState.SOLUTION, true);
					currentVertex.setState(SolutionState.SOLUTION, true);
					return true;
				}
			}
		}
		// No exit here.
		return false;
	}

}
