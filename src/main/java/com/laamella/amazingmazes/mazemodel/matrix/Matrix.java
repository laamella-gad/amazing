package com.laamella.amazingmazes.mazemodel.matrix;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;

public interface Matrix<T> {
    void set(Position position, T value);

    T get(Position position);

    Size getSize();

    abstract class Visitor<T> {
        protected abstract void visit(Position position, T value);

        protected void endRow() {
        }

        protected void startRow() {
        }
    }

    default void visitAllSquares(Visitor<T> visitor) {
        for (int y = 0; y < getSize().height; y++) {
            visitor.startRow();
            for (int x = 0; x < getSize().width; x++) {
                Position position = new Position(x, y);
                visitor.visit(position, get(position));
            }
            visitor.endRow();
        }
    }
}
