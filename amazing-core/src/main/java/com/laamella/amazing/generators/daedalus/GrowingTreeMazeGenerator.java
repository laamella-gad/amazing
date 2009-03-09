package com.laamella.amazing.generators.daedalus;

import java.util.*;

import com.laamella.amazing.generators.GraphMazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.graph.*;

/**
 * This is a general algorithm, capable of creating Mazes of different textures.
 * It requires storage up to the size of the Maze.
 * <p>
 * Each time you carve a cell, add that cell to a list. Proceed by picking a
 * cell from the list, and carving into an unmade cell next to it. If there are
 * no unmade cells next to the current cell, remove the current cell from the
 * list. The Maze is done when the list becomes empty.
 * <p>
 * The interesting part that allows many possible textures is how you pick a
 * cell from the list. For example, if you always pick the most recent cell
 * added to it, this algorithm turns into the recursive backtracker. If you
 * always pick cells at random, this will behave similarly but not exactly to
 * Prim's algorithm. If you always pick the oldest cells added to the list, this
 * will create Mazes with about as low a "river" factor as possible, even lower
 * than Prim's algorithm. If you usually pick the most recent cell, but
 * occasionally pick a random cell, the Maze will have a high "river" factor but
 * a short direct solution. If you randomly pick among the most recent cells,
 * the Maze will have a low "river" factor but a long windy solution.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
public class GrowingTreeMazeGenerator implements GraphMazeGenerator {
	private final Randomizer randomizer;

	public GrowingTreeMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(final Graph graph) {
		final List<Vertex> vertexList = new ArrayList<Vertex>();
		putAStartVertexInTheList(graph, vertexList);
		do {
			final Vertex randomVertex = pickAVertexFromTheList(vertexList, randomizer);
			final Edge edge = pickEdgeToUnvisitedVertex(randomVertex);
			if (edge == null) {
				vertexList.remove(randomVertex);
			} else {
				edge.setState(MazeDefinitionState.PASSAGE, true);
				final Vertex destinationVertex = edge.travel(randomVertex);
				destinationVertex.setState(VISITED_WHILE_GENERATING, true);
				vertexList.add(destinationVertex);
			}
		} while (vertexList.size() > 0);
	}

	private void putAStartVertexInTheList(final Graph graph, final List<Vertex> vertexList) {
		final Vertex startVertex = randomizer.pickOne(graph.getVertices());
		vertexList.add(startVertex);
		startVertex.setState(VISITED_WHILE_GENERATING, true);
	}

	private Edge pickEdgeToUnvisitedVertex(final Vertex randomVertex) {
		final Set<Edge> possibleEdges = new HashSet<Edge>();
		for (final Edge edge : randomVertex.getEdges()) {
			final Vertex destinationVertex = edge.travel(randomVertex);
			if (!destinationVertex.hasState(VISITED_WHILE_GENERATING)) {
				possibleEdges.add(edge);
			}
		}
		final Edge edge = randomizer.pickOne(possibleEdges);
		return edge;
	}

	/**
	 * You can override this to implement the behaviour as mentioned in the
	 * documentation above. The default implementation picks any element.
	 * 
	 * @param vertexList
	 *            pick a vertex from this list.
	 * @param randomizer
	 *            useful for randomizing your pick.
	 * @return a vertex from the list.
	 */
	protected Vertex pickAVertexFromTheList(final List<Vertex> vertexList, final Randomizer randomizer) {
		return randomizer.pickOne(vertexList);
	}

}
