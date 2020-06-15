package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Marker;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;

public interface Grid extends Graph {
    Square getSquare(Position position);

    Size getSize();

    Logger log = LoggerFactory.getLogger(Grid.class);

    @Deprecated
    default void closeAllWalls() {
        log.debug("closeAllWalls");
        forAllSquares((position, square) -> {
            square.getWall(UP).close();
            square.getWall(RIGHT).close();
            square.getWall(DOWN).close();
            square.getWall(LEFT).close();
            return null;
        });
    }

    default void openAllWalls() {
        log.debug("openAllWalls");
        forAllSquares((position, square) -> {
            square.getWall(UP).open();
            square.getWall(RIGHT).open();
            square.getWall(DOWN).open();
            square.getWall(LEFT).open();
            return null;
        });
    }

    @FunctionalInterface
    interface SquareVisitor<T> {
        T visitSquare(Position position, Square square);
    }

    default <T> T forAllSquares(SquareVisitor<T> visitor) {
        for (int y = 0; y < getSize().height; y++) {
            for (int x = 0; x < getSize().width; x++) {
                Position position = new Position(x, y);
                T t = visitor.visitSquare(position, getSquare(position));
                if (t != null) {
                    return t;
                }
            }
        }
        return null;
    }

    default boolean isBorderSquare(Position position) {
        return isBorderSquare(UP, position) || //
                isBorderSquare(RIGHT, position) || //
                isBorderSquare(LEFT, position) || //
                isBorderSquare(DOWN, position);
    }

    default boolean isBorderSquare(Direction direction, Position position) {
        return switch (direction) {
            case UP -> position.y == 0;
            case DOWN -> position.y == getSize().height - 1;
            case RIGHT -> position.x == getSize().width - 1;
            case LEFT -> position.x == 0;
        };
    }

    default Square randomSquare(Randomizer randomGenerator) {
        return getSquare(randomGenerator.randomPosition(getSize()));
    }

    default Square getTopLeftSquare() {
        return getSquare(new Position(0, 0));
    }

    default Square getBottomRightSquare() {
        return getSquare(new Position(getSize().width - 1, getSize().height - 1));
    }

    default void drawVerticalWall(int x, int y1, int y2) {
        for (int y = y1; y <= y2; y++) {
            getHorizontalWall(new Position(x, y)).close();
        }
    }

    default void drawHorizontalWall(int y, int x1, int x2) {
        for (int x = x1; x <= x2; x++) {
            getVerticalWall(new Position(x, y)).close();
        }
    }

    default Wall getHorizontalWall(Position position) {
        if (position.x < getSize().width) {
            return getSquare(position).getWall(LEFT);
        }
        return getSquare(position.move(LEFT.getMove())).getWall(RIGHT);
    }

    default Wall getVerticalWall(Position position) {
        if (position.y < getSize().height) {
            return getSquare(position).getWall(UP);
        }
        return getSquare(position.move(UP.getMove())).getWall(DOWN);
    }

    default void clearMarker(Marker marker) {
        for (Edge edge : getEdges()) {
            edge.unmark(marker);
        }
        for (Vertex vertex : getVertices()) {
            vertex.unmark(marker);
        }
    }

}
