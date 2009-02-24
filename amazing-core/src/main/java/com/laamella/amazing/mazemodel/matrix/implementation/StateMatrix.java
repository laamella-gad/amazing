package com.laamella.amazing.mazemodel.matrix.implementation;

import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.ENTRANCE;
import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.EXIT;
import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.OPEN;
import static com.laamella.amazing.solvers.Solver.SolutionState.SOLUTION;

import java.util.Observable;
import java.util.Observer;

import com.laamella.amazing.generators.MazeGenerator.GeneratorState;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper.MatrixVisitor;

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

	public String getPrintableMaze() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<ObservableObjectSetState>(this).visitAllSquares(new MatrixVisitor<ObservableObjectSetState>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(final Position position, final ObservableObjectSetState states) {
				if (states.hasState(ENTRANCE)) {
					maze.append(">");
				} else if (states.hasState(EXIT)) {
					maze.append("E");
				} else if (states.hasState(SOLUTION)) {
					maze.append('.');
				} else if (states.hasState(GeneratorState.VISITED)) {
					maze.append(' ');
				} else if (states.hasState(OPEN)) {
					maze.append(' ');
				} else {
					maze.append('#');
				}
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	@Override
	public String toString() {
		return getPrintableMaze();
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
