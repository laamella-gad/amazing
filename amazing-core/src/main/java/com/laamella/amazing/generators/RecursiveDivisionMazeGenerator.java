package com.laamella.amazing.generators;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.orthogonal.Grid;

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
 * Source: http://en.wikipedia.org/wiki/Maze_generation_algorithm
 */
public class RecursiveDivisionMazeGenerator implements MazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(RecursiveDivisionMazeGenerator.class);

	private final Grid.UtilityWrapper grid;
	private final RandomGenerator randomGenerator;

	public RecursiveDivisionMazeGenerator(final Grid grid, final RandomGenerator randomGenerator) {
		this.grid = new Grid.UtilityWrapper(grid);
		this.randomGenerator = randomGenerator;
	}

	public void generateMaze() {
		subdivide(grid.getTopLeftSquare().getPosition(), grid.getBottomRightSquare().getPosition().move(1, 1));
	}

	private void subdivide(final Position topLeft, final Position bottomRight) {
		log.debug("Subdividing [" + topLeft + "]-[" + bottomRight + "]");
		if (bottomRight.x - topLeft.x < 2 || bottomRight.y - topLeft.y < 2) {
			// Too little space to subdivide
			return;
		}
		final Position crossing = new Position(randomGenerator.between(topLeft.x, bottomRight.x), randomGenerator.between(topLeft.y, bottomRight.y));
		grid.drawVerticalWall(crossing.x, topLeft.y, bottomRight.y - 1);
		grid.drawHorizontalWall(crossing.y, topLeft.x, bottomRight.x - 1);
		// TODO make holes in walls.
		subdivide(topLeft, crossing);
		subdivide(crossing, bottomRight);
		subdivide(new Position(crossing.x, topLeft.y), new Position(bottomRight.x, crossing.y));
		subdivide(new Position(topLeft.x, crossing.y), new Position(crossing.x, bottomRight.y));
	}
}
