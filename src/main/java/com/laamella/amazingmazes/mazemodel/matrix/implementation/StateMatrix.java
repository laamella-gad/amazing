package com.laamella.amazingmazes.mazemodel.matrix.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Stateful.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;
import com.laamella.amazingmazes.observe.Observable;
import com.laamella.amazingmazes.observe.Observer;

public class StateMatrix extends Observable implements Observer, Matrix<ObservableObjectSetState> {
    private final ListMatrix<ObservableObjectSetState> matrix;

    public StateMatrix(Size size) {
        matrix = new ListMatrix<>(size) {
            @Override
            protected ObservableObjectSetState newItem() {
                ObservableObjectSetState state = new ObservableObjectSetState();
                state.addObserver(StateMatrix.this);
                return state;
            }
        };
    }

    @Override
    public void update() {
        setChanged();
        notifyObservers();
    }

    @Override
    public ObservableObjectSetState get(Position position) {
        return matrix.get(position);
    }

    @Override
    public Size getSize() {
        return matrix.getSize();
    }

    @Override
    public void set(Position position, ObservableObjectSetState value) {
        matrix.set(position, value);
    }

}
