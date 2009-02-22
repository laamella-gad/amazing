package com.laamella.amazing.generators.perfect;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.mazemodel.orthogonal.Grid;

/**
 * <p>
 * http://www.cut-the-knot.org/Curriculum/Geometry/PeanoComplete.shtml
 * <p>
 * http://www.nio.ntnu.no/archive/2000_2001/2/b1.c
 */
public class PeanoMazeGenerator implements MazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(PeanoMazeGenerator.class);
	private final Grid.UtilityWrapper grid;
	private final int degree;

	public PeanoMazeGenerator(Grid grid, int degree) {
		log.entry("PeanoMazeGenerator");
		this.grid = new Grid.UtilityWrapper(grid);
		this.degree = degree;
		log.exit("PeanoMazeGenerator");
	}

	void right(boolean mirror) {
		if (mirror) {
			left();
		} else {
			right();
		}
	}

	private void right() {
		log.debug("Right 90");
	}

	private void left() {
		log.debug("Left 90");
	}

	void left(boolean mirror) {
		if (mirror) {
			right();
		} else {
			left();
		}
	}

	void forward() {
		log.debug("Step");
	}

	/**
	 * @param mirror
	 *            specifies the orientation of the maze: false: left-turning,
	 *            true: right-turning
	 */
	void maze(int deg, boolean mirror) {
		if (deg == 1) {
			forward();
			left(mirror);
			forward();
			left(mirror);
			forward();
		} else {
			left(mirror);
			maze(deg - 1, !mirror);
			left(mirror);
			forward();
			maze(deg - 1, mirror);
			right(mirror);
			forward();
			right(mirror);
			maze(deg - 1, mirror);
			forward();
			left(mirror);
			maze(deg - 1, !mirror);
			left(mirror);
		}
	}

	public void generateMaze() {
		log.entry("generateMaze");
		grid.closeAllWalls();
		maze(degree, false);
		log.exit("generateMaze");
	}

}
