package com.laamella.amazing.mazemodel;

import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper.MatrixVisitor;

public class StateMatrix extends ArrayMatrix<Set<Object>> {
	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Set<Object> newItem() {
		return new HashSet<Object>();
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
					sum += state.hashCode();
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
