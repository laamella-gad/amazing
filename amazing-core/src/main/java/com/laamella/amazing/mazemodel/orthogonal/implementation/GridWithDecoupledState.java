package com.laamella.amazing.mazemodel.orthogonal.implementation;

import static com.laamella.amazing.mazemodel.MazeDefinitionState.PASSAGE;
import static com.laamella.amazing.mazemodel.orthogonal.Direction.DOWN;
import static com.laamella.amazing.mazemodel.orthogonal.Direction.LEFT;
import static com.laamella.amazing.mazemodel.orthogonal.Direction.RIGHT;
import static com.laamella.amazing.mazemodel.orthogonal.Direction.UP;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.mazemodel.*;
import com.laamella.amazing.mazemodel.graph.*;
import com.laamella.amazing.mazemodel.matrix.ArrayUtilities;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;
import com.laamella.amazing.mazemodel.orthogonal.Wall;

/**
 * Grid knows about relationships between squares and walls, but knows nothing
 * about their state. That is delegated to objects returned from the
 * storageFactory.
 */
public class GridWithDecoupledState implements Grid {
	private static int DEBUG_ID = 0;

	private final SquareDefault[][] squares;
	private final Size size;
	private final WallDefault[][] horizontalWalls;
	private final WallDefault[][] verticalWalls;
	private Set<Edge> edges = new HashSet<Edge>();
	private Set<Vertex> vertices = new HashSet<Vertex>();

	public GridWithDecoupledState(final GridStateStorage stateStorage) {
		this.size = stateStorage.getSize();
		squares = new SquareDefault[size.width][size.height];

		// TODO this is becoming a little dramatic

		horizontalWalls = new WallDefault[size.width][size.height + 1];
		verticalWalls = new WallDefault[size.width + 1][size.height];
		ArrayUtilities.visit2dArray(horizontalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final WallDefault wall = new WallDefault(stateStorage, position, true, GridWithDecoupledState.this);
				horizontalWalls[position.x][position.y] = wall;
				if (position.y > 0 && position.y < getSize().height) {
					edges.add(wall);
				}
			}
		});
		ArrayUtilities.visit2dArray(verticalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final WallDefault wall = new WallDefault(stateStorage, position, false, GridWithDecoupledState.this);
				verticalWalls[position.x][position.y] = wall;
				if (position.x > 0 && position.x < getSize().width) {
					edges.add(wall);
				}
			}
		});
		ArrayUtilities.visit2dArray(squares, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				final SquareDefault square = new SquareDefault(stateStorage, position, GridWithDecoupledState.this);
				squares[position.x][position.y] = square;
				vertices.add(square);
				if (position.x == 0 || position.y == 0 || position.x == size.width - 1 || position.y == size.height - 1) {
					square.setState(MazeGenerator.POSSIBLE_EXIT, true);
				}
			}
		});

		ArrayUtilities.visit2dArray(horizontalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				if (position.y == 0 || position.y == getSize().height) {
					return;
				}
				final Square squareAbove = getSquare(position.move(UP.getMove()));
				final Square squareBelow = getSquare(position);
				horizontalWalls[position.x][position.y].connect(squareAbove, squareBelow);
			}
		});
		ArrayUtilities.visit2dArray(verticalWalls, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				if (position.x == 0 || position.x == getSize().width) {
					return;
				}
				final Square squareLeft = getSquare(position.move(LEFT.getMove()));
				final Square squareRight = getSquare(position);
				verticalWalls[position.x][position.y].connect(squareLeft, squareRight);
			}
		});
		ArrayUtilities.visit2dArray(squares, new ArrayUtilities.Visitor2dArray() {
			public void visit(Position position) {
				squares[position.x][position.y].connect();
			}
		});
	}

	public Size getSize() {
		return size;
	}

	public Square getSquare(Position position) {
		return squares[position.x][position.y];
	}

	public Wall getHorizontalWall(int x, int y) {
		return horizontalWalls[x][y];
	}

	public Wall getVerticalWall(int x, int y) {
		return verticalWalls[x][y];
	}

	@Override
	public Set<Edge> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	@Override
	public Set<Vertex> getVertices() {
		return Collections.unmodifiableSet(vertices);
	}

	public static class SquareDefault implements Square {
		private final Position position;
		private DirectionMap<Wall> wallMap;
		private DirectionMap<Square> squareMap;
		private Set<Edge> edges;
		private final GridWithDecoupledState grid;
		private final State stateStorage;
		private final int id;

		public SquareDefault(final GridStateStorage stateStorage, final Position position, final GridWithDecoupledState grid) {
			this.stateStorage = stateStorage.getSquareState(position);
			this.position = position;
			this.grid = grid;
			this.id = DEBUG_ID++;
		}

		void connect() {
			wallMap = new DirectionMap<Wall>(grid.getHorizontalWall(position.x, position.y), grid.getVerticalWall(position.x + 1, position.y), grid
					.getHorizontalWall(position.x, position.y + 1), grid.getVerticalWall(position.x, position.y));
			edges = new HashSet<Edge>();
			squareMap = new DirectionMap<Square>();
			if (position.y > 0) {
				squareMap.up = grid.getSquare(position.move(UP.getMove()));
				edges.add(getWall(UP));
			}
			if (position.x < grid.getSize().width - 1) {
				squareMap.right = grid.getSquare(position.move(RIGHT.getMove()));
				edges.add(getWall(RIGHT));
			}
			if (position.y < grid.getSize().height - 1) {
				squareMap.down = grid.getSquare(position.move(DOWN.getMove()));
				edges.add(getWall(DOWN));
			}
			if (position.x > 0) {
				squareMap.left = grid.getSquare(position.move(LEFT.getMove()));
				edges.add(getWall(LEFT));
			}
		}

		public Wall getWall(Direction wall) {
			return wallMap.get(wall);
		}

		public Square getSquare(Direction direction) {
			return squareMap.get(direction);
		}

		public Position getPosition() {
			return position;
		}

		public boolean hasState(Object state) {
			return stateStorage.hasState(state);
		}

		public void setState(Object newState, boolean hasOrNot) {
			stateStorage.setState(newState, hasOrNot);
		}

		@Override
		public Set<Edge> getEdges() {
			return edges;
		}

		@Override
		public Graph getGraph() {
			return grid;
		}

		@Override
		public int getState(Object state) {
			return stateStorage.getState(state);
		}

		@Override
		public void setState(Object state, int value) {
			stateStorage.setState(state, value);
		}

		@Override
		public String toString() {
			return "[Square " + id + "]";
		}
	}

	public static class WallDefault implements Wall {
		private Square squareA;
		private Square squareB;

		private final State stateStorage;
		private final GridWithDecoupledState grid;
		private final int id;

		public WallDefault(final GridStateStorage stateStorage, final Position position, final boolean horizontal, final GridWithDecoupledState grid) {
			this.stateStorage = stateStorage.getWallState(position, horizontal);
			this.grid = grid;
			this.id = DEBUG_ID++;
		}

		void connect(Square squareA, Square squareB) {
			this.squareA = squareA;
			this.squareB = squareB;
		}

		public boolean isOpen() {
			return stateStorage.hasState(PASSAGE);
		}

		public void setOpened(boolean open) {
			stateStorage.setState(PASSAGE, open);
		}

		public void close() {
			setOpened(false);
		}

		public void open() {
			setOpened(true);
		}

		public boolean hasState(Object state) {
			return stateStorage.hasState(state);
		}

		public void setState(Object newState, boolean hasOrNot) {
			stateStorage.setState(newState, hasOrNot);
		}

		@Override
		public Vertex getVertexA() {
			return squareA;
		}

		@Override
		public Vertex getVertexB() {
			return squareB;
		}

		@Override
		public Vertex travel(final Vertex sourceVertex) {
			if (sourceVertex.equals(squareA)) {
				return squareB;
			}
			if (sourceVertex.equals(squareB)) {
				return squareA;
			}
			throw new IllegalArgumentException("Can't travel, edge does not belong to vertex.");
		}

		@Override
		public Graph getGraph() {
			return grid;
		}

		@Override
		public int getState(Object state) {
			return stateStorage.getState(state);
		}

		@Override
		public void setState(Object state, int value) {
			stateStorage.setState(state, value);
		}

		@Override
		public String toString() {
			return "[Wall " + id + "]";
		}
	}
}
