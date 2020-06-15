package com.laamella.amazingmazes.generators.daedalus;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.generators.RowMazeGenerator;
import com.laamella.amazingmazes.generators.Sets;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.RowGenerator;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import com.laamella.amazingmazes.mazemodel.grid.Wall;

import java.util.List;
import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;

/**
 * This algorithm is special because it's not only faster than all the others,
 * but its creation is also the most memory efficient. It doesn't even require
 * the whole Maze to be in memory, only using storage proportional to the size
 * of a row.
 * <p>
 * It creates the Maze one row at a time, where once a row has been generated,
 * the algorithm no longer looks at it. Each cell in a row is contained in a
 * set, where two cells are in the same set if there's a path between them
 * through the part of the Maze that's been made so far. This information allows
 * passages to be carved in the current row without creating loops or
 * isolations. This is actually quite similar to Kruskal's algorithm, just this
 * completes one row at a time, while Kruskal's looks over the whole Maze.
 * Creating a row consists of two parts: Randomly connecting adjacent cells
 * within a row, i.e. carving horizontal passages, then randomly connecting
 * cells between the current row and the next row, i.e. carving vertical
 * passages. When carving horizontal passages, don't connect cells already in
 * the same set (as that would create a loop), and when carving vertical
 * passages, you must connect a cell if it's a set of size one (as abandoning it
 * would create an isolation). When carving horizontal passages, when connecting
 * cells union the sets they're in (since there's now a path between them), and
 * when carving vertical passages, when not connecting a cell put it in a set by
 * itself (since it's now disconnected from the rest of the Maze). Creation
 * starts with each cell in its own set before connecting cells within the first
 * row, and creation ends after connecting cells within the last row, with a
 * special final rule that every cell must be in the same set by the time we're
 * done to prevent isolations. (The last row is done by connecting each pair of
 * adjacent cells if not already in the same set.)
 * <p>
 * One issue with this algorithm is that it's not balanced with respect to how
 * it treats the different edges of the Maze, where connecting vs. not
 * connecting cells need to be done in the right proportions to prevent texture
 * blemishes.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 * <p>
 * <a href="http://newsgroups.derkeiler.com/Archive/Rec/rec.games.roguelike.development/2005-08/msg00372.html"
 * >Modified Eller's algorithm</a>
 */
public class EllerMazeGenerator implements RowMazeGenerator {
    private final Randomizer randomizer;

    public EllerMazeGenerator(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    @Override
    public void generateMaze(RowGenerator rowGenerator) {
        Sets<Integer> squareSets = new Sets<>();
        generateFirstRow(rowGenerator.nextRow(), squareSets);
        do {
            generateRow(rowGenerator.nextRow(), squareSets);
        } while (rowGenerator.rowsToGo() > 1);
        generateLastRow(rowGenerator.nextRow(), squareSets);
    }

    private void generateLastRow(List<Square> squares, Sets<Integer> squareSets) {
        for (int x = 0; x < squares.size() - 1; x++) {
            Square square = squares.get(x);
            Set<Integer> thisSet = squareSets.findSetContaining(x);
            // remove horizontal walls where squares in different sets
            makeHorizontalPassage(squareSets, x, thisSet, square, true);
        }
    }

    private void generateRow(List<Square> squares, Sets<Integer> squareSets) {
        for (int x = 0; x < squares.size(); x++) {
            Square square = squares.get(x);
            Set<Integer> thisSet = squareSets.findSetContaining(x);

            if (x < squares.size() - 1) {
                makeHorizontalPassage(squareSets, x, thisSet, square, false);
            }
            makeVerticalPassage(squareSets, x, thisSet, square);
        }
    }

    private void makeVerticalPassage(Sets<Integer> squareSets, int x, Set<Integer> thisSet, Square square) {
        Wall downWall = square.getWall(DOWN);

        // When square is alone in a set, connect it vertically
        if (thisSet.size() == 1) {
            downWall.setOpened(true);
        } else {
            // Connect randomly
            if (randomizer.chance(0.5)) {
                downWall.setOpened(true);
            } else {
                // When not making a vertical passage, put square in its own
                // set.
                squareSets.putInNewSet(x);
                squareSets.removeFromSet(thisSet, x);
            }
        }
    }

    private void makeHorizontalPassage(Sets<Integer> squareSets, int x, Set<Integer> thisSet, Square square, boolean force) {
        Set<Integer> nextSet = squareSets.findSetContaining(x + 1);
        Wall rightWall = square.getWall(RIGHT);

        // Don't connect squares in the same set
        if (thisSet != nextSet) {
            // Connect randomly
            if (randomizer.chance(0.5) || force) {
                rightWall.setOpened(true);
                // When connecting, union the sets
                squareSets.unionSets(thisSet, nextSet);
            }
        }
    }

    private void generateFirstRow(List<Square> squares, Sets<Integer> squareSets) {
        for (int x = 0; x < squares.size(); x++) {
            squareSets.putInNewSet(x);
        }
        generateRow(squares, squareSets);
        Square entrance = randomizer.pickOne(squares);
        entrance.getWall(UP).setState(PASSAGE, true);
        entrance.setState(ENTRANCE, true);
    }

}
