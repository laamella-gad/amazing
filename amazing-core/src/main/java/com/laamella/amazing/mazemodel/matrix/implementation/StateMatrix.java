package com.laamella.amazing.mazemodel.matrix.implementation;

import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper.MatrixVisitor;

public class StateMatrix extends ListMatrix<Set<Object>> {
	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Set<Object> newItem() {
		return new HashSet<Object>(4);
	}

	@Override
	public String toString() {
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
				maze.append(toChar(sum));
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	private char toChar(int value) {
		return (char) (value % 26 + 'A');
	}

}
