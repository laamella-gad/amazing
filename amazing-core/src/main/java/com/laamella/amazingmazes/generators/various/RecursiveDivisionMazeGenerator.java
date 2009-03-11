package com.laamella.amazingmazes.generators.various;

import java.util.Observable;

import org.grlea.log.SimpleLogger;

import com.laamella.amazingmazes.generators.GridMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.Grid;

/**
 * Another simple algorithm for rectangular mazes, recursive division, works as
 * follows. Begin with a rectangular space with no walls. Call this a chamber.
 * Build at random points two walls that are perpendicular to each other. These
 * two walls divide the large chamber into four smaller chambers separated by
 * four walls. Choose three of the four walls at random, and open a one
 * cell-wide hole at a random point in each of the three. Continuing in this
 * manner recursively, until every chamber has a width of one cell in either of
 * the two directions, one can easily generate interesting mazes that avoid the
 * ease of solution of binary tree mazes.
 * <p>
 * Source: <a
 * href="http://en.wikipedia.org/wiki/Maze_generation_algorithm">Wikipedia</a>
 */
// TODO code is very ugly, create better code.
// TODO maybe the ability to create subgrids is useful?
public class RecursiveDivisionMazeGenerator extends Observable implements GridMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(RecursiveDivisionMazeGenerator.class);

	private final Randomizer randomizer;

	public RecursiveDivisionMazeGenerator(final Randomizer randomGenerator) {
		this.randomizer = randomGenerator;
	}

	@Override
	public void generateMaze(final Grid plainGrid) {
		final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
		grid.openAllWalls();
		grid.drawHorizontalWall(0, 0, grid.getSize().width - 1);
		grid.drawHorizontalWall(grid.getSize().height, 0, grid.getSize().width - 1);
		grid.drawVerticalWall(0, 0, grid.getSize().height - 1);
		grid.drawVerticalWall(grid.getSize().width, 0, grid.getSize().height - 1);
		setChanged();
		notifyObservers();
		subdivide(grid, grid.getTopLeftSquare().getPosition(), grid.getBottomRightSquare().getPosition().move(1, 1));
	}

	private void subdivide(final Grid.UtilityWrapper grid, final Position topLeft, final Position bottomRight) {
		log.debug("Subdividing [" + topLeft + "]-[" + bottomRight + "]");
		if (bottomRight.x - topLeft.x < 2 || bottomRight.y - topLeft.y < 2) {
			// Too little space to subdivide
			return;
		}
		final Position crossing = new Position(randomizer.between(topLeft.x, bottomRight.x - 1) + 1, randomizer.between(topLeft.y, bottomRight.y - 1) + 1);
		
		grid.drawVerticalWall(crossing.x, topLeft.y, bottomRight.y - 1);
		grid.drawHorizontalWall(crossing.y, topLeft.x, bottomRight.x - 1);

		final int wallToIgnore = randomizer.random(4);
		if (wallToIgnore != 0) {
			// make hole in wall pointing up.
			final int y = randomizer.between(topLeft.y, crossing.y);
			grid.getHorizontalWall(new Position(crossing.x, y)).open();
		}
		if (wallToIgnore != 1) {
			// make hole in wall pointing down.
			final int y = randomizer.between(crossing.y, bottomRight.y);
			grid.getHorizontalWall(new Position(crossing.x, y)).open();
		}

		if (wallToIgnore != 2) {
			// make hole in wall pointing right.
			final int x = randomizer.between(crossing.x, bottomRight.x);
			grid.getVerticalWall(new Position(x, crossing.y)).open();
		}

		if (wallToIgnore != 3) {
			// make hole in wall pointing left.
			final int x = randomizer.between(topLeft.x, crossing.x);
			grid.getVerticalWall(new Position(x, crossing.y)).open();
		}

		setChanged();
		notifyObservers();

		// Recurse into the four new chambers made by drawing the horizontal and
		// vertical wall.
		subdivide(grid, topLeft, crossing);
		subdivide(grid, crossing, bottomRight);
		subdivide(grid, new Position(crossing.x, topLeft.y), new Position(bottomRight.x, crossing.y));
		subdivide(grid, new Position(topLeft.x, crossing.y), new Position(crossing.x, bottomRight.y));
	}

}
