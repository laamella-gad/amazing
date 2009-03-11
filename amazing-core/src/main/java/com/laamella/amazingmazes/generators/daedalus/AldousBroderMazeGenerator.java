package com.laamella.amazingmazes.generators.daedalus;

import java.util.HashSet;
import java.util.Set;

import org.grlea.log.SimpleLogger;

import com.laamella.amazingmazes.generators.GraphMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.graph.*;

/**
 * The interesting thing about this algorithm is it generates all possible Mazes
 * of a given size with equal probability. It also requires no extra storage or
 * stack.
 * <p>
 * Pick a point, and move to a neighboring cell at random. If an uncarved cell
 * is entered, carve into it from the previous cell. Keep moving to neighboring
 * cells until all cells have been carved into.
 * 
 * <p>
 * Start at any node.
 * <ol>
 * <li>If all nodes have been visited, halt.
 * <li>Choose a neighbor of the current node uniformly at random.
 * <li>If the node has never been visited before, add the edge to that node to
 * the spanning tree.
 * <li>Make the neighbor the current node and go back to step 1.
 * </ol>
 * <p>
 * This algorithm yields Mazes with a low "river" factor, only slightly higher
 * than Kruskal's algorithm. (This means for a given size there are more Mazes
 * with a low "river" factor than high "river", since an average equal
 * probability Maze has low "river".) The bad thing about this algorithm is that
 * it's very slow, since it doesn't do any intelligent hunting for the last
 * cells, where in fact it's not even guaranteed to terminate. However since the
 * algorithm is simple it can move over many cells quickly, so finishes faster
 * than one might think. On average it takes about seven times longer to run
 * than the above algorithms, although in bad cases it can take much longer if
 * the random number generator keeps making it avoid the last few cells. This
 * can be done as a wall adder if the boundary wall is treated as a single
 * vertex, i.e. if a move goes to the boundary wall, teleport to a random point
 * along the boundary before moving again. As a wall adder this runs nearly
 * twice as fast, because the boundary wall teleportation allows quicker access
 * to distant parts of the Maze.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 * <p>
 * <a href="http://forums.xkcd.com/viewtopic.php?f=12&t=34293">Quick description
 * on the XKCD forums</a>
 */
public class AldousBroderMazeGenerator implements GraphMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(AldousBroderMazeGenerator.class);

	private final Randomizer randomizer;

	public AldousBroderMazeGenerator(final Randomizer randomGenerator) {
		this.randomizer = randomGenerator;
	}

	@Override
	public void generateMaze(Graph graph) {
		log.entry("generateMaze()");
		final Set<Vertex> visitedVertices = new HashSet<Vertex>();
		Vertex currentVertex = new Graph.UtilityWrapper(graph).getEntrance();
		visitedVertices.add(currentVertex);

		while (visitedVertices.size() < graph.getVertices().size()) {
			final Edge randomEdge = randomizer.pickOne(currentVertex.getEdges());
			final Vertex randomVertex = randomEdge.travel(currentVertex);
			if (!visitedVertices.contains(randomVertex)) {
				randomEdge.setState(MazeDefinitionState.PASSAGE, true);
				visitedVertices.add(randomVertex);
			}
			currentVertex = randomVertex;
		}

		log.exit("generateMaze()");
	}
}
