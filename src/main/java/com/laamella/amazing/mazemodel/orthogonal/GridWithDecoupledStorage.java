package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.mazemodel.ArrayUtilities;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;

/**
 * Grid knows about relationships between squares and walls, but knows nothing
 * about their state. That is delegated to objects returned from the
 * storageFactory.
 */
public class GridWithDecoupledStorage implements Grid {
	private final Square[][] squares;
	private final Size size;
	private final Wall[][] horizontalWalls;
	private final Wall[][] verticalWalls;
	private Square exit;
	private Square entrance;

	public GridWithDecoupledStorage(final GridStorageFactory storageFactory) {
		this.size = storageFactory.getSize();
		squares = new SquareDefault[size.width][size.height];

		horizontalWalls = new Wall[size.width][size.height + 1];
		verticalWalls = new Wall[size.width + 1][size.height];
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
				squares[position.x][position.y] = new SquareDefault(squareStorage, position, GridWithDecoupledStorage.this);
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

		private final SquareStorage storage;
		private final Position position;
		private final GridWithDecoupledStorage grid;
		private DirectionMap<Wall> wallMap;
		private DirectionMap<Square> squareMap;

		public SquareDefault(SquareStorage squareStorage, Position position, GridWithDecoupledStorage grid) {
			this.storage = squareStorage;
			this.position = position;
			this.grid = grid;
		}

		public Wall getWall(Direction wall) {
			if (wallMap == null) {
				wallMap = new DirectionMap<Wall>(grid.getHorizontalWall(position.x, position.y), grid.getVerticalWall(position.x + 1, position.y), grid
						.getHorizontalWall(position.x, position.y + 1), grid.getVerticalWall(position.x, position.y));
			}
			return wallMap.get(wall);
		}

		public Square getSquare(Direction direction) {
			if (squareMap == null) {
				squareMap = new DirectionMap<Square>();
				if (position.y > 0) {
					squareMap.up = grid.getSquare(position.move(Direction.UP.getMove()));
				}
				if (position.x < grid.getSize().width - 1) {
					squareMap.right = grid.getSquare(position.move(Direction.RIGHT.getMove()));
				}
				if (position.y < grid.getSize().height - 1) {
					squareMap.down = grid.getSquare(position.move(Direction.DOWN.getMove()));
				}
				if (position.x > 0) {
					squareMap.left = grid.getSquare(position.move(Direction.LEFT.getMove()));
				}
			}
			return squareMap.get(direction);
		}

		public Position getPosition() {
			return position;
		}

		public boolean hasState(int state) {
			return storage.hasState(state);
		}

		public void setState(int newState, boolean hasOrNot) {
			storage.setState(newState, hasOrNot);
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

		public void close() {
			storage.setOpened(false);
		}

		public void open() {
			storage.setOpened(true);
		}

		public boolean hasState(int state) {
			return storage.hasState(state);
		}

		public void setState(int newState, boolean hasOrNot) {
			setState(newState, hasOrNot);
		}

	}

	public Wall getHorizontalWall(int x, int y) {
		return horizontalWalls[x][y];
	}

	public Wall getVerticalWall(int x, int y) {
		return verticalWalls[x][y];
	}

	public Square getEntrance() {
		return entrance;
	}

	public Square getExit() {
		return exit;
	}

	public void setEntrance(Square entrance) {
		this.entrance = entrance;
	}

	public void setExit(Square exit) {
		this.exit = exit;
	}

}
