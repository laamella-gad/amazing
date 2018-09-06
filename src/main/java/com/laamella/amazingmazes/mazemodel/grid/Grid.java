package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public interface Grid extends Graph {
    Square getSquare(Position position);

    Size getSize();

    class UtilityWrapper implements Grid {
        private static Logger log = LoggerFactory.getLogger(Grid.UtilityWrapper.class);
        private final Grid delegateGrid;

        public UtilityWrapper(final Grid delegateGrid) {
            this.delegateGrid = delegateGrid;
        }

        @Deprecated
        public void closeAllWalls() {
            log.debug("closeAllWalls");
            forAllSquares(new SquareVisitor<Void>() {
                @Override
                public Void visitSquare(final Position position, final Square square) {
                    square.getWall(Direction.UP).close();
                    square.getWall(Direction.RIGHT).close();
                    square.getWall(Direction.DOWN).close();
                    square.getWall(Direction.LEFT).close();
                    return null;
                }
            });
        }

        public void openAllWalls() {
            log.debug("openAllWalls");
            forAllSquares(new SquareVisitor<Void>() {
                @Override
                public Void visitSquare(final Position position, final Square square) {
                    square.getWall(Direction.UP).open();
                    square.getWall(Direction.RIGHT).open();
                    square.getWall(Direction.DOWN).open();
                    square.getWall(Direction.LEFT).open();
                    return null;
                }
            });
        }

        @FunctionalInterface
        public interface SquareVisitor<T> {
            T visitSquare(Position position, Square square);
        }

        public <T> T forAllSquares(final SquareVisitor<T> visitor) {
            for (int y = 0; y < delegateGrid.getSize().height; y++) {
                for (int x = 0; x < delegateGrid.getSize().width; x++) {
                    final Position position = new Position(x, y);
                    final T t = visitor.visitSquare(position, delegateGrid.getSquare(position));
                    if (t != null) {
                        return t;
                    }
                }
            }
            return null;
        }

        public boolean isBorderSquare(final Position position) {
            return isBorderSquare(Direction.UP, position) || //
                    isBorderSquare(Direction.RIGHT, position) || //
                    isBorderSquare(Direction.LEFT, position) || //
                    isBorderSquare(Direction.DOWN, position);
        }

        public boolean isBorderSquare(final Direction direction, final Position position) {
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

        @Override
        public Size getSize() {
            return delegateGrid.getSize();
        }

        @Override
        public Square getSquare(final Position position) {
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

        public void drawVerticalWall(final int x, final int y1, final int y2) {
            for (int y = y1; y <= y2; y++) {
                getHorizontalWall(new Position(x, y)).close();
            }
        }

        public void drawHorizontalWall(final int y, final int x1, final int x2) {
            for (int x = x1; x <= x2; x++) {
                getVerticalWall(new Position(x, y)).close();
            }
        }

        public Wall getHorizontalWall(final Position position) {
            if (position.x < delegateGrid.getSize().width) {
                return getSquare(position).getWall(Direction.LEFT);
            }
            return getSquare(position.move(Direction.LEFT.getMove())).getWall(Direction.RIGHT);
        }

        public Wall getVerticalWall(final Position position) {
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

        public void clearState(final Object state) {
            for (final Edge edge : getEdges()) {
                edge.setState(state, false);
            }
            for (final Vertex vertex : getVertices()) {
                vertex.setState(state, false);
            }
        }

    }
}
