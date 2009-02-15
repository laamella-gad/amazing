package com.laamella.amazing.generators;

import static com.laamella.amazing.mazemodel.orthogonal.Square.Direction.*;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;

/**
 * This is basically the simplest and fastest algorithm possible, however Mazes
 * produced by it have a very biased texture. For each cell carve a passage
 * either leading up or leading left, but not both. In the wall added version,
 * for each vertex add a wall segment leading down or right, but not both. Each
 * cell is independent of every other cell, where you don't have to refer to the
 * state of any other cells when creating it. Hence this is a true memoryless
 * Maze generation algorithm, with no limit to the size of Maze you can create.
 * This is basically a computer science binary tree, if you consider the upper
 * left corner the root, where each node or cell has one unique parent which is
 * the cell above or to the left of it. Binary tree Mazes are different than
 * standard perfect Mazes, since about half the cell types can never exist in
 * them. For example there will never be a crossroads, and all dead ends have
 * passages pointing up or left, and never down or right. The Maze tends to have
 * passages leading diagonally from upper left to lower right, where the Maze is
 * much easier to navigate from lower right to upper left. You will always be
 * able to travel up or left, but never both, so you can always
 * deterministically travel diagonally up and to the left without hitting any
 * barriers. Traveling down and to the right is when you'll encounter choices
 * and dead ends. Note if you flip a binary tree Maze upside down and treat
 * passages as walls and vice versa, the result is basically another binary
 * tree.
 */
public class BinaryTreeMazeGenerator implements MazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(BinaryTreeMazeGenerator.class);
	private final Grid.GridUtilityWrapper grid;
	private final RandomGenerator randomGenerator;

	public BinaryTreeMazeGenerator(final Grid grid, final RandomGenerator randomGenerator) {
		this.grid = new Grid.GridUtilityWrapper(grid);
		this.randomGenerator = randomGenerator;
	}

	public void generateMaze() {
		log.entry("createMaze");
		grid.closeAllWalls();

		grid.forAllSquares(new Grid.GridUtilityWrapper.SquareVisitor() {
			public void visitSquare(Position position, Square square) {
				if (grid.isBorderSquare(LEFT, position)) {
					if (grid.isBorderSquare(UP, position)) {
						// Create upper left entrance
						square.getWall(LEFT).open();
					} else {
						// Open the whole left column vertically
						square.getWall(UP).open();
					}
					return;
				}
				if (grid.isBorderSquare(UP, position)) {
					// Open the whole top row horizontally
					square.getWall(LEFT).open();
					return;
				}
				// Pick either left or up
				if (randomGenerator.chance(0.5)) {
					square.getWall(LEFT).open();
				} else {
					square.getWall(UP).open();
				}
			}
		});

		// Lower right exit
		grid.getSquare(new Position(grid.getSize().width - 1, grid.getSize().height - 1)).getWall(RIGHT).open();

		log.exit("createMaze");
	}
}
