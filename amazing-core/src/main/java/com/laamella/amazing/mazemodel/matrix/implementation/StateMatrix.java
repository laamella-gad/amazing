package com.laamella.amazing.mazemodel.matrix.implementation;

import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.State;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper.MatrixVisitor;

public class StateMatrix extends ListMatrix<Set<Object>> {
	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Set<Object> newItem() {
		return new HashSet<Object>(4);
	}

	public String getPrintableStateMatrix() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<Set<Object>>(this).visitAllSquares(new MatrixVisitor<Set<Object>>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, Set<Object> states) {
				int sum = 0;
				for (Object state : states) {
					sum += state.getClass().getName().charAt(0);
				}
				maze.append((char) (sum % 26 + 'A'));
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	public String getPrintableMaze() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<Set<Object>>(this).visitAllSquares(new MatrixVisitor<Set<Object>>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, Set<Object> states) {
				if (states.contains(State.ENTRANCE)) {
					maze.append(">");
				} else if (states.contains(State.EXIT)) {
					maze.append("E");
				} else if (states.contains(State.OPEN)) {
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
}
