package com.laamella.amazing.generators.original;

import java.util.*;

import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.generators.RowMazeGenerator;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.orthogonal.*;

/**
 * This algorithm is special because it's not only faster than all the others,
 * but its creation is also the most memory efficient. It doesn't even require
 * the whole Maze to be in memory, only using storage proportional to the size
 * of a row. It creates the Maze one row at a time, where once a row has been
 * generated, the algorithm no longer looks at it. Each cell in a row is
 * contained in a set, where two cells are in the same set if there's a path
 * between them through the part of the Maze that's been made so far. This
 * information allows passages to be carved in the current row without creating
 * loops or isolations. This is actually quite similar to Kruskal's algorithm,
 * just this completes one row at a time, while Kruskal's looks over the whole
 * Maze. Creating a row consists of two parts: Randomly connecting adjacent
 * cells within a row, i.e. carving horizontal passages, then randomly
 * connecting cells between the current row and the next row, i.e. carving
 * vertical passages. When carving horizontal passages, don't connect cells
 * already in the same set (as that would create a loop), and when carving
 * vertical passages, you must connect a cell if it's a set of size one (as
 * abandoning it would create an isolation). When carving horizontal passages,
 * when connecting cells union the sets they're in (since there's now a path
 * between them), and when carving vertical passages, when not connecting a cell
 * put it in a set by itself (since it's now disconnected from the rest of the
 * Maze). Creation starts with each cell in its own set before connecting cells
 * within the first row, and creation ends after connecting cells within the
 * last row, with a special final rule that every cell must be in the same set
 * by the time we're done to prevent isolations. (The last row is done by
 * connecting each pair of adjacent cells if not already in the same set.) One
 * issue with this algorithm is that it's not balanced with respect to how it
 * treats the different edges of the Maze, where connecting vs. not connecting
 * cells need to be done in the right proportions to prevent texture blemishes.
 * <p>
 * <a href="http://newsgroups.derkeiler.com/Archive/Rec/rec.games.roguelike.development/2005-08/msg00372.html"
 * >Modified Eller's algorithm</a>
 * 
 * <pre>
 * ###1#######
 * #2#1111111# (first level, one disjoint room)
 * 
 * ###1#######
 * #2#1111111#
 * #2#1#######
 * #2#11111#3# (2nd level, a new disjoint room, 2nd set has grown to a
 * 'tower')
 * 
 * ###1#######
 * #2#1111111#
 * #2#1#######
 * #2#11111#3#
 * #2###1#1#3#
 * #222#1#1#3# (3rd level, nothing very new)
 * 
 * ###1#######
 * #2#1111111#
 * #2#1#######
 * #2#11111#1#
 * #2###1#1#1#
 * #222#1#1#1#
 * ###2#1#1#1#
 * #222#1#111# (4th level, 3rd set has joined with the first)
 * 
 * ###1#######
 * #1#1111111#
 * #1#1#######
 * #1#11111#1#
 * #1###1#1#1#
 * #111#1#1#1#
 * ###1#1#1#1#
 * #111#1#111#
 * #1###1###1#
 * #11111#111# (5th and final level, all sets are now joined)
 * ###########
 * </pre>
 */
public class EllerMazeGenerator implements RowMazeGenerator {
	private final Randomizer randomizer;

	public EllerMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(final RowGenerator rowGenerator) {
		final Set<Set<Integer>> squareSets = new TreeSet<Set<Integer>>();
		generateFirstRow(rowGenerator.nextRow(), squareSets);
		do {
			generateRow(rowGenerator.nextRow(), squareSets);
		} while (rowGenerator.rowsToGo() > 1);
		generateLastRow(rowGenerator.nextRow(), squareSets);
	}

	private void generateLastRow(final List<Square> squares, final Set<Set<Integer>> squareSets) {
		// remove horizontal walls where squares in different sets
		// 
	}

	private void generateRow(final List<Square> squares, final Set<Set<Integer>> squareSets) {

	}

	private void generateFirstRow(final List<Square> squares, final Set<Set<Integer>> squareSets) {
		generateRow(squares, squareSets);
		final Square entrance = randomizer.pickOne(squares);
		entrance.getWall(Direction.UP).setState(MazeDefinitionState.PASSAGE, true);
		entrance.setState(MazeDefinitionState.ENTRANCE, true);
		for (int x = 0; x < squares.size(); x++) {
			final Set<Integer> set = new HashSet<Integer>();
			set.add(x);
			squareSets.add(set);
		}
	}
}
