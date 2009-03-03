package com.laamella.amazing.generators.daedalus;

import static com.laamella.amazing.mazemodel.MazeDefinitionState.*;

import java.util.HashSet;
import java.util.Set;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.*;
import com.laamella.amazing.mazemodel.graph.*;

/**
 * This requires storage proportional to the size of the Maze. During creation,
 * each cell is one of three types:
 * <ol>
 * <li>"In": The cell is part of the Maze and has been carved into already,
 * <li>"Frontier": The cell is not part of the Maze and has not been carved into
 * yet, but is next to a cell that's already "in", and
 * <li>"Out": The cell is not part of the Maze yet, and none of its neighbors
 * are "in" either.
 * </ol>
 * <p>
 * Start by picking a cell, making it "in", and setting all its neighbors to
 * "frontier". Proceed by picking a "frontier" cell at random, and carving into
 * it from one of its neighbor cells that are "in". Change that "frontier" cell
 * to "in", and update any of its neighbors that are "out" to "frontier".
 * <p>
 * The Maze is done when there are no more "frontier" cells left (which means
 * there are no more "out" cells left either, so they're all "in"). This
 * algorithm results in Mazes with a very low "river" factor, with many short
 * dead ends, and the solution is usually pretty direct as well. It also runs
 * very fast when implemented right, with only Eller's algorithm being faster.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
// TODO there's a variant that puts the edges on the frontier
public class PrimMazeGenerator implements GraphMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(PrimMazeGenerator.class);

	private final Randomizer randomizer;

	public PrimMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(final Graph graph) {
		log.entry("generateMaze");
		
		final Set<Vertex> in = new HashSet<Vertex>();
		final Set<Vertex> frontier = new HashSet<Vertex>();
		final Set<Vertex> out = new HashSet<Vertex>();
		out.addAll(graph.getVertices());

		final Vertex startVertex = randomizer.pickOne(out);
		in.add(startVertex);
		out.remove(startVertex);

		makeFrontier(startVertex, frontier, out);

		while (frontier.size() > 0) {
			log.debug("in: " + in.size() + ", frontier: " + frontier.size() + ", out: " + out.size());
			final Vertex currentFrontierVertex = randomizer.pickOne(frontier);
			for (final Edge edge : currentFrontierVertex.getEdges()) {
				final Vertex possibleInVertex = edge.travel(currentFrontierVertex);
				if (in.contains(possibleInVertex)) {
					edge.setState(PASSAGE, true);
					in.add(currentFrontierVertex);
					frontier.remove(currentFrontierVertex);
					makeFrontier(currentFrontierVertex, frontier, out);
					break;
				}
			}
		}
		log.exit("generateMaze");
	}

	private void makeFrontier(final Vertex vertex, final Set<Vertex> frontier, final Set<Vertex> out) {
		for (final Edge edge : vertex.getEdges()) {
			final Vertex possibleFrontierVertex = edge.travel(vertex);
			if (out.contains(possibleFrontierVertex)) {
				out.remove(possibleFrontierVertex);
				frontier.add(possibleFrontierVertex);
			}
		}
	}

}
