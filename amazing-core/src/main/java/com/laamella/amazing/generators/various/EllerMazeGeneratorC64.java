package com.laamella.amazing.generators.various;

import static com.laamella.amazing.mazemodel.orthogonal.Direction.*;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.GridMazeGenerator;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;

/**
 * Converted from a BASIC type in listing, found in a magazine somewhere in the
 * eighties.
 */
// TODO convert to progressive row based algorithm
public class EllerMazeGeneratorC64 implements GridMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(EllerMazeGeneratorC64.class);
	private final double steepness;

	public EllerMazeGeneratorC64(final double steepness) {
		this.steepness = steepness;
	}

	private void messWithLR_0(int x, int[] l, int[] r) {
		int pos = x - 1;

		while (r[pos] != 0) {
			pos = r[pos];
		}

		l[r[x]] = pos;
		r[pos] = r[x];
	}

	private void messWithLR_1(int x, int[] l, int[] r) {
		int pos = x;

		while (l[pos] != 0) {
			pos = l[pos];
		}

		r[l[x - 1]] = pos;
		l[pos] = l[x - 1];
	}

	private void setSquareOpenRightAndBottom(final Grid grid, int x, int y, boolean openRight, boolean openBottom) {
		final Square square = grid.getSquare(new Position(x, y));
		square.getWall(RIGHT).setOpened(openRight);
		square.getWall(DOWN).setOpened(openBottom);
	}

	@Override
	public void generateMaze(final Grid plainGrid) {
		final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
		log.entry("generateMaze");

		final int width = grid.getSize().width;
		final int height = grid.getSize().height;
		final int[] left = new int[width + 1];
		final int[] right = new int[width + 1];

		grid.closeAllWalls();

		log.debug("Top entrance");
		grid.getSquare(new Position(random(width), 0)).getWall(UP).open();

		// Print maze
		for (int y = 1; y < height; y++) {
			log.debug("New row");
			for (int x = width; x >= 1; x--) {
				final boolean openRight = isOpenToTheRight(steepness, left, right, x);
				final boolean openBottom = isOpenAtTheBottom(steepness, left, right, x);
				setSquareOpenRightAndBottom(grid, width - x, y - 1, openRight, openBottom);
			}
		}

		log.debug("Finishing maze with last row");
		setSquareOpenRightAndBottom(grid, 0, height - 1, false, true);
		final int exitX = random(width) + 1;

		for (int x = width; x >= 1; x--) {
			if (right[x] == x - 1 || (right[x] != 0) && randomVertical(steepness)) {
				setSquareOpenRightAndBottom(grid, width - x, height - 1, false, x == exitX);
			} else {
				messWithLR_0(x, left, right);
				setSquareOpenRightAndBottom(grid, width - x, height - 1, true, x == exitX);
			}
		}
		log.exit("generateMaze");
	}

	private boolean isOpenAtTheBottom(double steepness, final int[] l, final int[] r, int x) {
		if ((l[x] + r[x] != 0) && randomHorizontal(steepness)) {
			l[r[x]] = l[x];
			r[l[x]] = r[x];
			l[x] = 0;
			r[x] = 0;
			return false;
		}
		return true;
	}

	private boolean isOpenToTheRight(double steepness, final int[] l, final int[] r, int x) {
		if (r[x] == x - 1 || randomVertical(steepness)) {
			return false;
		}
		if (r[x] != 0) {
			messWithLR_0(x, l, r);
		}
		if (l[x - 1] != 0) {
			messWithLR_1(x, l, r);
		}
		r[x] = x - 1;
		l[x - 1] = x;
		return true;
	}

	private int random(int width) {
		return (int) (Math.random() * width);
	}

	private boolean randomHorizontal(double steepness) {
		return Math.random() > steepness;
	}

	private boolean randomVertical(double steepness) {
		return Math.random() < steepness;
	}
}
