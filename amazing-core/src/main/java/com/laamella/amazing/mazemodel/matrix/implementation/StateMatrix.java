package com.laamella.amazing.mazemodel.matrix.implementation;

import java.util.Observable;
import java.util.Observer;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazing.mazemodel.matrix.Matrix;

public class StateMatrix extends Observable implements Observer, Matrix<ObservableObjectSetState> {
	private final ListMatrix<ObservableObjectSetState> matrix;

	public StateMatrix(final Size size) {
		matrix = new ListMatrix<ObservableObjectSetState>(size) {
			@Override
			protected ObservableObjectSetState newItem() {
				final ObservableObjectSetState state = new ObservableObjectSetState();
				state.addObserver(StateMatrix.this);
				return state;
			}
		};
	}

	@Override
	public void update(final Observable o, final Object arg) {
		setChanged();
		notifyObservers();
	}

	@Override
	public ObservableObjectSetState get(final Position position) {
		return matrix.get(position);
	}

	@Override
	public Size getSize() {
		return matrix.getSize();
	}

	@Override
	public void set(final Position position, final ObservableObjectSetState value) {
		matrix.set(position, value);
	}

}
