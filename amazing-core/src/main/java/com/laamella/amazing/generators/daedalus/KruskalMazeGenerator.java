package com.laamella.amazing.generators.daedalus;

import java.util.*;

import com.laamella.amazing.generators.*;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.graph.*;

/**
 * This algorithm is interesting because it doesn't "grow" the Maze like a tree,
 * but rather carves passage segments all over the Maze at random, but yet still
 * results in a perfect Maze in the end. It requires storage proportional to the
 * size of the Maze, along with the ability to enumerate each edge or wall
 * between cells in the Maze in random order (which usually means creating a
 * list of all edges and shuffling it randomly). Label each cell with a unique
 * id, then loop over all the edges in random order. For each edge, if the cells
 * on either side of it have different id's, then erase the wall, and set all
 * the cells on one side to have the same id as those on the other. If the cells
 * on either side of the wall already have the same id, then there already
 * exists some path between those two cells, so the wall is left alone so as to
 * not create a loop. This algorithm yields Mazes with a low "river" factor, but
 * not as low as Prim's algorithm. Merging the two sets on either side of the
 * wall will be a slow operation if each cell just has a number and are merged
 * by a loop. Merging as well as lookup can be done in near constant time by
 * giving each cell a node in a tree structure, with the id at the root, where
 * merging is done quickly by splicing the trees together.
 * <p>
 * <a href="http://www.cs.man.ac.uk/~graham/cs2022/greedy/index.html">Nice
 * interactive Kruskal</a>
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 * <p>
 * Implementation uses sets instead of marking vertices with id's.
 */
public class KruskalMazeGenerator implements GraphMazeGenerator {

	private final Randomizer randomizer;

	public KruskalMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(final Graph graph) {
		final Vertex entranceVertex = new Graph.UtilityWrapper(graph).getEntrance();

		// Put all vertices in a set by themselves.
		final Sets<Vertex> sets = new Sets<Vertex>();
		for (final Vertex vertex : entranceVertex.getGraph().getVertices()) {
			sets.putInNewSet(vertex);
		}

		do {
			// Pick a random edge.
			// (Normal Kruskal would take the edge with the lowest weight here.)
			final Edge edge = randomizer.pickOne(graph.getEdges());

			// See in which sets the corresponding vertices are.
			final Vertex vertexA = edge.getVertexA();
			final Vertex vertexB = edge.getVertexB();
			final Set<Vertex> setA = sets.findCorrespondingSet(vertexA);
			final Set<Vertex> setB = sets.findCorrespondingSet(vertexB);

			// If they are in different sets, we can connect them.
			if (setA != setB) {
				edge.setState(MazeDefinitionState.PASSAGE, true);
				sets.unionSets(setA, setB);
			}
			// Stop when all vertices are in the same set
		} while (sets.size() > 1);
	}
}
