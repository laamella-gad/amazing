package com.laamella.daedalousy.mazemodel.orthogonal;

import java.util.HashMap;
import java.util.Map;

import com.laamella.daedalousy.mazemodel.ArrayUtilities;
import com.laamella.daedalousy.mazemodel.Matrix;

public class GridToMatrixAdapter implements Grid {
	private final int heightInSquares;
	private final int widthInSquares;
	private final Square[][] squares;

	public GridToMatrixAdapter(final Matrix mazeMatrix, final Matrix stateMatrix) {
		this.widthInSquares = mazeMatrix.getWidth() / 3;
		this.heightInSquares = mazeMatrix.getHeight() / 3;
		this.squares = new Square[widthInSquares][heightInSquares];
		ArrayUtilities.visit2dArray(squares, new ArrayUtilities.Visitor2dArray() {
			public void visit(int x, int y) {
				squares[x][y] = new MatrixSquare(squares, mazeMatrix, stateMatrix, x * 3 + 1, y * 3 + 1);
			}
		});
	}

	public Square getSquare(int x, int y) {
		return squares[x][y];
	}

	public int getHeightInSquares() {
		return heightInSquares;
	}

	public int getHeightInWalls() {
		return getHeightInSquares() + 1;
	}

	public int getWidthInSquares() {
		return widthInSquares;
	}

	public int getWidthInWalls() {
		return getWidthInSquares() + 1;
	}

	public static class MatrixSquare implements Square {
		private final int x;
		private final int y;
		private final Matrix stateMatrix;
		private final Map<Direction, MatrixWall> walls = new HashMap<Direction, MatrixWall>();
		private final Map<Direction, MatrixSquare> squares=new HashMap<Direction, MatrixSquare>();

		public MatrixSquare(final Square[][] squares, final Matrix mazeMatrix, final Matrix stateMatrix, final int x, final int y) {
			this.stateMatrix = stateMatrix;
			this.x = x;
			this.y = y;
			walls.put(Direction.NORTH, new MatrixWall(mazeMatrix, stateMatrix, xForWall(Direction.NORTH, x), yForWall(Direction.NORTH, y)));
			walls.put(Direction.EAST, new MatrixWall(mazeMatrix, stateMatrix, xForWall(Direction.EAST, x), yForWall(Direction.EAST, y)));
			walls.put(Direction.SOUTH, new MatrixWall(mazeMatrix, stateMatrix, xForWall(Direction.SOUTH, x), yForWall(Direction.SOUTH, y)));
			walls.put(Direction.WEST, new MatrixWall(mazeMatrix, stateMatrix, xForWall(Direction.WEST, x), yForWall(Direction.WEST, y)));
		}
		
		private int yForWall(Direction wall, int y) {
			switch (wall) {
			case NORTH:
				return y - 1;
			case SOUTH:
				return y + 1;
			default:
				return y;
			}
		}

		private int xForWall(Direction wall, int x) {
			switch (wall) {
			case EAST:
				return x + 1;
			case WEST:
				return x - 1;
			default:
				return x;
			}

		}

		public Wall getWall(Direction direction) {
			return walls.get(direction);
		}

		public int getState() {
			return stateMatrix.get(x, y);
		}

		public void setState(int newState) {
			stateMatrix.set(x, y, newState);
		}

		public Square getSquare(Direction direction) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class MatrixWall implements Wall {
		private final Matrix mazeMatrix;
		private final Matrix stateMatrix;
		private final int x;
		private final int y;

		private MatrixWall(final Matrix mazeMatrix, final Matrix stateMatrix, final int x, final int y) {
			this.mazeMatrix = mazeMatrix;
			this.stateMatrix = stateMatrix;
			this.x = x;
			this.y = y;
		}

		public boolean isOpen() {
			return mazeMatrix.get(x, y) == Matrix.SOLID;
		}

		public void setOpened(boolean solid) {
			mazeMatrix.set(x, y, solid ? Matrix.SOLID : Matrix.CLEAR);
		}

		public int getState() {
			return stateMatrix.get(x, y);
		}

		public void setState(int newState) {
			stateMatrix.set(x, y, newState);
		}

		public void close() {
			setOpened(false);
		}

		public void open() {
			setOpened(true);
		}

	}
}
