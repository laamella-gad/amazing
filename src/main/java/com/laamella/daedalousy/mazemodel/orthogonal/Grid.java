package com.laamella.daedalousy.mazemodel.orthogonal;

import org.grlea.log.SimpleLogger;

import com.laamella.daedalousy.mazemodel.orthogonal.Square.Direction;

public interface Grid {
	Square getSquare(int x, int y);

	int getWidthInSquares();

	int getWidthInWalls();

	int getHeightInSquares();

	int getHeightInWalls();

	public static class GridUtilityWrapper implements Grid {
		private static final SimpleLogger log = new SimpleLogger(GridUtilityWrapper.class);
		private final Grid delegateGrid;

		public GridUtilityWrapper(Grid delegateGrid) {
			this.delegateGrid=delegateGrid;
		}

		public void closeAllWalls() {
			log.entry("closeAllWalls");
			forAllSquares(new SquareVisitor() {
				public void visitSquare(int x, int y, Square square) {
					square.getWall(Direction.NORTH).setOpened(true);
					square.getWall(Direction.EAST).setOpened(true);
					square.getWall(Direction.SOUTH).setOpened(true);
					square.getWall(Direction.WEST).setOpened(true);
				}
			});
			log.exit("closeAllWalls");
		}

		public static interface SquareVisitor {
			void visitSquare(int x, int y, Square square);
		}

		public void forAllSquares(final SquareVisitor visitor) {
			for (int x = 0; x < delegateGrid.getWidthInSquares(); x++) {
				for (int y = 0; y < delegateGrid.getHeightInSquares(); y++) {
					visitor.visitSquare(x, y, delegateGrid.getSquare(x, y));
				}
			}
		}

		public boolean isBorderSquare(int x, int y) {
			return isBorderSquare(Direction.NORTH, x, y) || //
					isBorderSquare(Direction.EAST, x, y) || //
					isBorderSquare(Direction.WEST, x, y) || //
					isBorderSquare(Direction.SOUTH, x, y);
		}

		public boolean isBorderSquare(Direction direction, int x, int y) {
			switch (direction) {
			case NORTH:
				return y == 0;
			case SOUTH:
				return y == delegateGrid.getHeightInSquares() - 1;
			case EAST:
				return x == delegateGrid.getWidthInSquares() - 1;
			case WEST:
				return x == 0;
			default:
				throw new IllegalStateException("Not a direction: " + direction.name());
			}
		}

		public int getHeightInSquares() {
			return delegateGrid.getHeightInSquares();
		}

		public int getHeightInWalls() {
			return delegateGrid.getHeightInWalls();
		}

		public Square getSquare(int x, int y) {
			return delegateGrid.getSquare(x, y);
		}

		public int getWidthInSquares() {
			return delegateGrid.getWidthInSquares();
		}

		public int getWidthInWalls() {
			return delegateGrid.getWidthInWalls();
		}
	}
}
