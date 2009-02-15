package com.laamella.amazing.mazemodel.orthogonal;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.orthogonal.Square.Direction;

public interface Grid {
	Square getSquare(Position position);

	Size getSize();

	Position getEntrance();

	Position getExit();

	public static class GridUtilityWrapper implements Grid {
		private static final SimpleLogger log = new SimpleLogger(GridUtilityWrapper.class);
		private final Grid delegateGrid;

		public GridUtilityWrapper(Grid delegateGrid) {
			this.delegateGrid = delegateGrid;
		}

		public void closeAllWalls() {
			log.entry("closeAllWalls");
			forAllSquares(new SquareVisitor() {
				public void visitSquare(Position position, Square square) {
					square.getWall(Direction.UP).close();
					square.getWall(Direction.RIGHT).close();
					square.getWall(Direction.DOWN).close();
					square.getWall(Direction.LEFT).close();
				}
			});
			log.exit("closeAllWalls");
		}

		public static interface SquareVisitor {
			void visitSquare(Position position, Square square);
		}

		public void forAllSquares(final SquareVisitor visitor) {
			for (int y = 0; y < delegateGrid.getSize().height; y++) {
				for (int x = 0; x < delegateGrid.getSize().width; x++) {
					final Position position = new Position(x, y);
					visitor.visitSquare(position, delegateGrid.getSquare(position));
				}
			}
		}

		public boolean isBorderSquare(Position position) {
			return isBorderSquare(Direction.UP, position) || //
					isBorderSquare(Direction.RIGHT, position) || //
					isBorderSquare(Direction.LEFT, position) || //
					isBorderSquare(Direction.DOWN, position);
		}

		public boolean isBorderSquare(Direction direction, Position position) {
			switch (direction) {
			case UP:
				return position.y == 0;
			case DOWN:
				return position.y == delegateGrid.getSize().height - 1;
			case RIGHT:
				return position.x == delegateGrid.getSize().width - 1;
			case LEFT:
				return position.x == 0;
			default:
				throw new IllegalStateException("Not a direction: " + direction.name());
			}
		}

		public Size getSize() {
			return delegateGrid.getSize();
		}

		public Square getSquare(Position position) {
			return delegateGrid.getSquare(position);
		}

		public Position getEntrance() {
			// TODO Auto-generated method stub
			return null;
		}

		public Position getExit() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
