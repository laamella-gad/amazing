package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;

/**
 * Converted from a BASIC type in listing, which I found somewhere in the
 * eighties.
 */
// TODO convert to progressive row based algorithm
// TODO make 0,0 based
public class EllerMazeGenerator implements MazeGenerator {
	private final Grid model;
	private final double steepness;

	public EllerMazeGenerator(final Grid model, final double steepness) {
		this.model = model;
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

	private void setSquareOpenRightAndBottom(int x, int y, boolean openRight, boolean openBottom) {
		final Square square = model.getSquare(new Position(x, y));
		square.getWall(Square.Direction.RIGHT).setOpened(openRight);
		square.getWall(Square.Direction.DOWN).setOpened(openBottom);
	}

	public void generateMaze() {
		final int width = model.getSize().width - 1;
		final int height = model.getSize().height - 1;
		final int[] left = new int[width + 1];
		final int[] right = new int[width + 1];
		final int entranceX = random(width);

		// Print upper maze border
		setSquareOpenRightAndBottom(0, 0, true, true);
		for (int x = 1; x <= width; x++) {
			setSquareOpenRightAndBottom(x, 0, true, x == entranceX);
			left[x] = 0;
			right[x] = 0;
		}

		// Print maze
		for (int y = 2; y <= height; y++) {
			setSquareOpenRightAndBottom(0, y - 1, false, true);
			for (int x = width; x >= 1; x--) {
				final boolean openRight = isOpenToTheRight(steepness, left, right, x);
				final boolean openBottom = isOpenAtTheBottom(steepness, left, right, x);
				setSquareOpenRightAndBottom(width - x + 1, y - 1, openRight, openBottom);
			}
		}

		// Print lower maze border
		setSquareOpenRightAndBottom(0, height, false, true);
		final int exitX = random(width);

		for (int x = width; x >= 1; x--) {
			if (right[x] == x - 1 || (right[x] != 0) && randomVertical(steepness)) {
				setSquareOpenRightAndBottom(width - x + 1, height, false, x == exitX);
			} else {
				messWithLR_0(x, left, right);
				setSquareOpenRightAndBottom(width - x + 1, height, true, x == exitX);
			}
		}
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
		return (int) (Math.random() * width) + 1;
	}

	private boolean randomHorizontal(double steepness) {
		return Math.random() > steepness;
	}

	private boolean randomVertical(double steepness) {
		return Math.random() < steepness;
	}
}
