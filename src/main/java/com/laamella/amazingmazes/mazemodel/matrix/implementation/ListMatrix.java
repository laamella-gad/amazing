package com.laamella.amazingmazes.mazemodel.matrix.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public abstract class ListMatrix<T> implements Matrix<T> {
    private final List<T> matrix;
    private final Size size;

    public ListMatrix(final Size size) {
        this.size = size;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < size.area; i++) {
            matrix.add(newItem());
        }
    }

    protected abstract T newItem();

    @Override
    public T get(final Position position) {
        if (position.x >= size.width) {
            throw new IndexOutOfBoundsException("x " + position.x + " >= " + size.width);
        }
        if (position.y >= size.height) {
            throw new IndexOutOfBoundsException("y " + position.y + " >= " + size.height);
        }
        if (position.x < 0) {
            throw new IndexOutOfBoundsException("x " + position.x + " < 0");
        }
        if (position.y < 0) {
            throw new IndexOutOfBoundsException("y " + position.y + " < 0");
        }
        return matrix.get(position.x + position.y * size.width);
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void set(final Position position, final T value) {
        matrix.set(position.x + position.y * size.width, value);
    }

}
