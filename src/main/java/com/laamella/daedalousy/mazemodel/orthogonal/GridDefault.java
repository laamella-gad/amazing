package com.laamella.daedalousy.mazemodel.orthogonal;

import java.util.HashMap;
import java.util.Map;

import com.laamella.daedalousy.mazemodel.ArrayUtilities;

public class GridDefault implements Grid {
	private final int widthInSquares;
	private final int heightInSquares;
	private final Square[][] cells;

	public GridDefault(int widthInSquares, int heightInSquares) {
		this.widthInSquares = widthInSquares;
		this.heightInSquares = heightInSquares;
		cells = new SquareDefault[widthInSquares][heightInSquares];
		final Wall[][] horizontalWalls = new Wall[widthInSquares][heightInSquares + 1];
		final Wall[][] verticalWalls = new Wall[widthInSquares + 1][heightInSquares];
		ArrayUtilities.visit2dArray(horizontalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(int x, int y) {
				horizontalWalls[x][y] = new WallDefault(false);
			}
		});
		ArrayUtilities.visit2dArray(verticalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(int x, int y) {
				verticalWalls[x][y] = new WallDefault(false);
			}
		});
		ArrayUtilities.visit2dArray(cells, new ArrayUtilities.Visitor2dArray() {
			public void visit(int x, int y) {
				cells[x][y] = new SquareDefault(horizontalWalls[x][y], verticalWalls[x + 1][y], horizontalWalls[x][y + 1], verticalWalls[x][y]);
			}
		});
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

	public Square getSquare(int x, int y) {
		return cells[x][y];
	}

	public static class SquareDefault implements Square {

		private final Map<Direction, Wall> walls;
		private int state;

		public SquareDefault(final Wall northWall, final Wall eastWall, final Wall southWall, final Wall westWall) {
			walls = new HashMap<Direction, Wall>();
			walls.put(Direction.NORTH, northWall);
			walls.put(Direction.EAST, eastWall);
			walls.put(Direction.SOUTH, southWall);
			walls.put(Direction.WEST, westWall);
			state = 0;
		}

		public int getState() {
			return state;
		}

		public void setState(int newState) {
			this.state = newState;
		}

		public Wall getWall(Direction wall) {
			return walls.get(wall);
		}
	}

	public static class WallDefault implements Wall {
		private boolean solid;
		private int state;

		public WallDefault(final boolean solid) {
			this.solid = solid;
			this.state = 0;
		}

		public boolean isOpen() {
			return solid;
		}

		public void setOpened(boolean solid) {
			this.solid = solid;
		}

		public int getState() {
			return state;
		}

		public void setState(int newState) {
			state = newState;
		}

		public void close() {
			setOpened(false);
		}

		public void open() {
			setOpened(true);
		}

	}

}
