package com.laamella.amazing.mazemodel.orthogonal;

import java.util.Set;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.graph.Edge;
import com.laamella.amazing.mazemodel.graph.Graph;
import com.laamella.amazing.mazemodel.graph.Vertex;

public interface Grid extends Graph {
	Square getSquare(Position position);

	Size getSize();

	public static class UtilityWrapper implements Grid {
		private static final SimpleLogger log = new SimpleLogger(UtilityWrapper.class);
		private final Grid delegateGrid;

		public UtilityWrapper(Grid delegateGrid) {
			this.delegateGrid = delegateGrid;
		}

		@Deprecated
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

		public Square randomSquare(final Randomizer randomGenerator) {
			return getSquare(randomGenerator.randomPosition(getSize()));
		}

		public Square getTopLeftSquare() {
			return getSquare(new Position(0, 0));
		}

		public Square getBottomRightSquare() {
			return getSquare(new Position(getSize().width - 1, getSize().height - 1));
		}

		public void drawVerticalWall(int x, int y1, int y2) {
			for (int y = y1; y <= y2; y++) {
				getHorizontalWall(new Position(x, y)).close();
			}
		}

		public void drawHorizontalWall(int y, int x1, int x2) {
			for (int x = x1; x <= x2; x++) {
				getVerticalWall(new Position(x, y)).close();
			}
		}

		public Wall getHorizontalWall(Position position) {
			if (position.x < delegateGrid.getSize().width) {
				return getSquare(position).getWall(Direction.LEFT);
			}
			return getSquare(position.move(Direction.LEFT.getMove())).getWall(Direction.RIGHT);
		}

		public Wall getVerticalWall(Position position) {
			if (position.y < delegateGrid.getSize().height) {
				return getSquare(position).getWall(Direction.UP);
			}
			return getSquare(position.move(Direction.UP.getMove())).getWall(Direction.DOWN);
		}

		@Override
		public Set<Edge> getEdges() {
			return delegateGrid.getEdges();
		}

		@Override
		public Set<Vertex> getVertices() {
			return delegateGrid.getVertices();
		}

	}
}
