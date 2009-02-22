package com.laamella.amazing.generators.perfect;

import org.grlea.log.SimpleLogger;

import static com.laamella.amazing.mazemodel.State.*;

import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;
import com.laamella.amazing.mazemodel.orthogonal.Grid.UtilityWrapper;

/**
 * The interesting thing about this algorithm is it generates all possible Mazes
 * of a given size with equal probability. It also requires no extra storage or
 * stack. Pick a point, and move to a neighboring cell at random. If an uncarved
 * cell is entered, carve into it from the previous cell. Keep moving to
 * neighboring cells until all cells have been carved into. This algorithm
 * yields Mazes with a low "river" factor, only slightly higher than Kruskal's
 * algorithm. (This means for a given size there are more Mazes with a low
 * "river" factor than high "river", since an average equal probability Maze has
 * low "river".) The bad thing about this algorithm is that it's very slow,
 * since it doesn't do any intelligent hunting for the last cells, where in fact
 * it's not even guaranteed to terminate. However since the algorithm is simple
 * it can move over many cells quickly, so finishes faster than one might think.
 * On average it takes about seven times longer to run than the above
 * algorithms, although in bad cases it can take much longer if the random
 * number generator keeps making it avoid the last few cells. This can be done
 * as a wall adder if the boundary wall is treated as a single vertex, i.e. if a
 * move goes to the boundary wall, teleport to a random point along the boundary
 * before moving again. As a wall adder this runs nearly twice as fast, because
 * the boundary wall teleportation allows quicker access to distant parts of the
 * Maze.
 */
public class AldousBroderMazeGenerator implements MazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(AldousBroderMazeGenerator.class);
	private final UtilityWrapper grid;
	private final Randomizer randomGenerator;

	public AldousBroderMazeGenerator(final Grid grid, final Randomizer randomGenerator) {
		this.grid = new Grid.UtilityWrapper(grid);
		this.randomGenerator = randomGenerator;
	}

	// TODO make entrance and exit
	public void generateMaze() {
		grid.closeAllWalls();

		grid.setEntrance(grid.getTopLeftSquare());
		grid.getEntrance().getWall(Direction.LEFT).open();
		grid.getEntrance().setState(VISITED, true);
		
		grid.setExit(grid.getBottomRightSquare());
		
		for (int i = 1; i < grid.getSize().area; i++) {
			log.debug("Cell " + i + " of " + grid.getSize().area);
			boolean carvedACell = false;
			while (!carvedACell) {
				final Square sourceSquare = grid.randomSquare(randomGenerator);
				if (sourceSquare.hasState(VISITED)) {
					final Direction carveDirection = Direction.random(randomGenerator);
					final Square destinationSquare = sourceSquare.getSquare(carveDirection);
					if (sourceSquare.getPosition().x == 0) {
						log.debug("Trying " + sourceSquare.getPosition() + " " + carveDirection.name());
					}
					if (destinationSquare != null) {
						if (!destinationSquare.hasState(VISITED)) {
							sourceSquare.getWall(carveDirection).open();
							destinationSquare.setState(VISITED, true);
							carvedACell = true;
						}
					}
				}
			}
		}
	}
}
