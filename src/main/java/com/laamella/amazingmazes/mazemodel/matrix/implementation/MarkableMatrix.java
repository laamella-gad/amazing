package com.laamella.amazingmazes.mazemodel.matrix.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Markable.ObservableMarkable;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;
import com.laamella.amazingmazes.observe.Observable;
import com.laamella.amazingmazes.observe.Observer;

public class MarkableMatrix extends Observable implements Observer, Matrix<ObservableMarkable> {
    private final ListMatrix<ObservableMarkable> matrix;

    public MarkableMatrix(Size size) {
        matrix = new ListMatrix<>(size) {
            @Override
            protected ObservableMarkable newItem() {
                ObservableMarkable markable = new ObservableMarkable();
                markable.addObserver(MarkableMatrix.this);
                return markable;
            }
        };
    }

    @Override
    public void update() {
        setChanged();
        notifyObservers();
    }

    @Override
    public ObservableMarkable get(Position position) {
        return matrix.get(position);
    }

    @Override
    public Size getSize() {
        return matrix.getSize();
    }

    @Override
    public void set(Position position, ObservableMarkable value) {
        matrix.set(position, value);
    }

}
