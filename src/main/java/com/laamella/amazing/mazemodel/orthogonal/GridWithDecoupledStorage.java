package com.laamella.amazing.mazemodel.orthogonal;

import java.util.HashMap;
import java.util.Map;

import com.laamella.amazing.mazemodel.ArrayUtilities;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;

public class GridWithDecoupledStorage implements Grid {
	private final Square[][] squares;
	private final Size size;

	public GridWithDecoupledStorage(final GridStorageFactory storageFactory) {
		this.size = storageFactory.getSize();
		squares = new SquareDefault[size.width][size.height];

		final Wall[][] horizontalWalls = new Wall[size.width][size.height + 1];
		final Wall[][] verticalWalls = new Wall[size.width + 1][size.height];
		ArrayUtilities.visit2dArray(horizontalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final WallStorage horizontalWallStorage = storageFactory.createHorizontalWallStorage(position);
				horizontalWalls[position.x][position.y] = new WallDefault(horizontalWallStorage);
			}
		});
		ArrayUtilities.visit2dArray(verticalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final WallStorage verticalWallStorage = storageFactory.createVerticalWallStorage(position);
				verticalWalls[position.x][position.y] = new WallDefault(verticalWallStorage);
			}
		});
		ArrayUtilities.visit2dArray(squares, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final SquareStorage squareStorage = storageFactory.createSquareStorage(position);
				squares[position.x][position.y] = new SquareDefault(squareStorage, horizontalWalls[position.x][position.y], verticalWalls[position.x + 1][position.y], horizontalWalls[position.x][position.y + 1], verticalWalls[position.x][position.y]);
			}
		});
	}

	public Size getSize() {
		return size;
	}

	public Square getSquare(Position position) {
		return squares[position.x][position.y];
	}

	public static class SquareDefault implements Square {

		private final Map<Direction, Wall> walls;
		private final SquareStorage storage;

		public SquareDefault(final SquareStorage squareStorage, final Wall northWall, final Wall eastWall, final Wall southWall, final Wall westWall) {
			walls = new HashMap<Direction, Wall>();
			walls.put(Direction.UP, northWall);
			walls.put(Direction.RIGHT, eastWall);
			walls.put(Direction.DOWN, southWall);
			walls.put(Direction.LEFT, westWall);
			this.storage = squareStorage;
		}

		public int getState() {
			return storage.getState();
		}

		public void setState(int newState) {
			storage.setState(newState);
		}

		public Wall getWall(Direction wall) {
			return walls.get(wall);
		}
	}

	public static class WallDefault implements Wall {
		private final WallStorage storage;

		public WallDefault(final WallStorage storage) {
			this.storage = storage;
		}

		public boolean isOpen() {
			return storage.isOpen();
		}

		public void setOpened(boolean open) {
			storage.setOpened(open);
		}

		public int getState() {
			return storage.getState();
		}

		public void setState(int newState) {
			storage.setState(newState);
		}

		public void close() {
			storage.setOpened(false);
		}

		public void open() {
			storage.setOpened(true);
		}

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
