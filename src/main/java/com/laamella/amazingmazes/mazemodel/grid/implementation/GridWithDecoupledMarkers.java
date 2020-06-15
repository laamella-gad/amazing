package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Marker;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Markable;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import com.laamella.amazingmazes.mazemodel.grid.Wall;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.laamella.amazingmazes.generators.MazeGenerator.*;
import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.PASSAGE;
import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;
import static com.laamella.amazingmazes.mazemodel.matrix.ArrayUtilities.visit2dArray;

/**
 * Grid knows about relationships between squares and walls, but knows nothing
 * about their state. That is delegated to objects returned from the
 * storageFactory.
 */
public class GridWithDecoupledMarkers implements Grid {
    private static int DEBUG_ID = 0;

    private final SquareDefault[][] squares;
    private final Size size;
    private final WallDefault[][] horizontalWalls;
    private final WallDefault[][] verticalWalls;
    private final Set<Edge> edges = new HashSet<>();
    private final Set<Vertex> vertices = new HashSet<>();

    public GridWithDecoupledMarkers(GridMarkerStorage storage) {
        this.size = storage.getSize();

        squares = new SquareDefault[size.width][size.height];
        horizontalWalls = new WallDefault[size.width][size.height + 1];
        verticalWalls = new WallDefault[size.width + 1][size.height];

        createGraphObjects(storage);
        connectGraphObjects();
    }

    private void createGraphObjects(GridMarkerStorage storage) {
        visit2dArray(horizontalWalls, position -> {
            WallDefault wall = new WallDefault(storage, position, true, GridWithDecoupledMarkers.this);
            horizontalWalls[position.x][position.y] = wall;
            if (position.y > 0 && position.y < getSize().height) {
                edges.add(wall);
            }
        });
        visit2dArray(verticalWalls, position -> {
            WallDefault wall = new WallDefault(storage, position, false, GridWithDecoupledMarkers.this);
            verticalWalls[position.x][position.y] = wall;
            if (position.x > 0 && position.x < getSize().width) {
                edges.add(wall);
            }
        });
        visit2dArray(squares, position -> {
            SquareDefault square = new SquareDefault(storage, position, GridWithDecoupledMarkers.this);
            squares[position.x][position.y] = square;
            vertices.add(square);
            if (position.x == 0 || position.y == 0 || position.x == size.width - 1 || position.y == size.height - 1) {
                square.mark(POSSIBLE_EXIT);
            }
        });
    }

    @Override
    public Size getSize() {
        return size;
    }

    private void connectGraphObjects() {
        visit2dArray(horizontalWalls, position -> {
            if (position.y == 0 || position.y == getSize().height) {
                return;
            }
            Square squareAbove = getSquare(position.move(UP.getMove()));
            Square squareBelow = getSquare(position);
            horizontalWalls[position.x][position.y].connect(squareAbove, squareBelow);
        });
        visit2dArray(verticalWalls, position -> {
            if (position.x == 0 || position.x == getSize().width) {
                return;
            }
            Square squareLeft = getSquare(position.move(LEFT.getMove()));
            Square squareRight = getSquare(position);
            verticalWalls[position.x][position.y].connect(squareLeft, squareRight);
        });
        visit2dArray(squares, position -> squares[position.x][position.y].connect());
    }

    @Override
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
        private final GridWithDecoupledMarkers grid;
        private final Markable markerStorage;
        private final int id;

        public SquareDefault(GridMarkerStorage storage, Position position, GridWithDecoupledMarkers grid) {
            this.markerStorage = storage.getMarkableSquare(position);
            this.position = position;
            this.grid = grid;
            this.id = DEBUG_ID++;
        }

        void connect() {
            wallMap = new DirectionMap<>(grid.getHorizontalWall(position.x, position.y), grid.getVerticalWall(
                    position.x + 1, position.y), grid.getHorizontalWall(position.x, position.y + 1),
                    grid.getVerticalWall(position.x, position.y));
            edges = new HashSet<>();
            squareMap = new DirectionMap<>();
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

        @Override
        public Wall getWall(Direction wall) {
            return wallMap.get(wall);
        }

        @Override
        public Square getSquare(Direction direction) {
            return squareMap.get(direction);
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public boolean isMarked(Marker marker) {
            return markerStorage.isMarked(marker);
        }

        @Override
        public void mark(Marker marker, boolean mustBeSet) {
            markerStorage.mark(marker, mustBeSet);
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
        public Integer getNumberMarker(Marker marker) {
            return markerStorage.getNumberMarker(marker);
        }

        @Override
        public void markNumber(Marker marker, int value) {
            markerStorage.markNumber(marker, value);
        }

        @Override
        public String toString() {
            return "[Square " + id + "]";
        }
    }

    public static class WallDefault implements Wall {
        private Square squareA;
        private Square squareB;

        private final Markable markerStorage;
        private final GridWithDecoupledMarkers grid;
        private final int id;

        public WallDefault(GridMarkerStorage markerStorage, Position position, boolean horizontal,
                           GridWithDecoupledMarkers grid) {
            this.markerStorage = markerStorage.getMarkableWall(position, horizontal);
            this.grid = grid;
            this.id = DEBUG_ID++;
        }

        void connect(Square squareA, Square squareB) {
            this.squareA = squareA;
            this.squareB = squareB;
        }

        @Override
        public boolean isOpen() {
            return markerStorage.isMarked(PASSAGE);
        }

        @Override
        public void setOpened(boolean open) {
            markerStorage.mark(PASSAGE, open);
        }

        @Override
        public void close() {
            setOpened(false);
        }

        @Override
        public void open() {
            setOpened(true);
        }

        @Override
        public boolean isMarked(Marker marker) {
            return markerStorage.isMarked(marker);
        }

        @Override
        public void mark(Marker marker, boolean mustBeSet) {
            markerStorage.mark(marker, mustBeSet);
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
        public Vertex travel(Vertex sourceVertex) {
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
        public Integer getNumberMarker(Marker marker) {
            return markerStorage.getNumberMarker(marker);
        }

        @Override
        public void markNumber(Marker marker, int value) {
            markerStorage.markNumber(marker, value);
        }

        @Override
        public String toString() {
            return "[Wall " + id + "]";
        }
    }
}
