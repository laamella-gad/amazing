package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Grid extends Graph {
    Square getSquare(Position position);

    Size getSize();

    Logger log = LoggerFactory.getLogger(Grid.class);

    @Deprecated
    default void closeAllWalls() {
        log.debug("closeAllWalls");
        forAllSquares((position, square) -> {
            square.getWall(Direction.UP).close();
            square.getWall(Direction.RIGHT).close();
            square.getWall(Direction.DOWN).close();
            square.getWall(Direction.LEFT).close();
            return null;
        });
    }

    default void openAllWalls() {
        log.debug("openAllWalls");
        forAllSquares((position, square) -> {
            square.getWall(Direction.UP).open();
            square.getWall(Direction.RIGHT).open();
            square.getWall(Direction.DOWN).open();
            square.getWall(Direction.LEFT).open();
            return null;
        });
    }

    @FunctionalInterface
    interface SquareVisitor<T> {
        T visitSquare(Position position, Square square);
    }

    default <T> T forAllSquares(final SquareVisitor<T> visitor) {
        for (int y = 0; y < getSize().height; y++) {
            for (int x = 0; x < getSize().width; x++) {
                final Position position = new Position(x, y);
                final T t = visitor.visitSquare(position, getSquare(position));
                if (t != null) {
                    return t;
                }
            }
        }
        return null;
    }

    default boolean isBorderSquare(final Position position) {
        return isBorderSquare(Direction.UP, position) || //
                isBorderSquare(Direction.RIGHT, position) || //
                isBorderSquare(Direction.LEFT, position) || //
                isBorderSquare(Direction.DOWN, position);
    }

    default boolean isBorderSquare(final Direction direction, final Position position) {
        return switch (direction) {
            case UP -> position.y == 0;
            case DOWN -> position.y == getSize().height - 1;
            case RIGHT -> position.x == getSize().width - 1;
            case LEFT -> position.x == 0;
        };
    }

    default Square randomSquare(final Randomizer randomGenerator) {
        return getSquare(randomGenerator.randomPosition(getSize()));
    }

    default Square getTopLeftSquare() {
        return getSquare(new Position(0, 0));
    }

    default Square getBottomRightSquare() {
        return getSquare(new Position(getSize().width - 1, getSize().height - 1));
    }

    default void drawVerticalWall(final int x, final int y1, final int y2) {
        for (int y = y1; y <= y2; y++) {
            getHorizontalWall(new Position(x, y)).close();
        }
    }

    default void drawHorizontalWall(final int y, final int x1, final int x2) {
        for (int x = x1; x <= x2; x++) {
            getVerticalWall(new Position(x, y)).close();
        }
    }

    default Wall getHorizontalWall(final Position position) {
        if (position.x < getSize().width) {
            return getSquare(position).getWall(Direction.LEFT);
        }
        return getSquare(position.move(Direction.LEFT.getMove())).getWall(Direction.RIGHT);
    }

    default Wall getVerticalWall(final Position position) {
        if (position.y < getSize().height) {
            return getSquare(position).getWall(Direction.UP);
        }
        return getSquare(position.move(Direction.UP.getMove())).getWall(Direction.DOWN);
    }

    default void clearState(final MazeState state) {
        for (final Edge edge : getEdges()) {
            edge.setState(state, false);
        }
        for (final Vertex vertex : getVertices()) {
            vertex.setState(state, false);
        }
    }

}
