package com.laamella.amazing.mazemodel;

import java.util.HashSet;
import java.util.Set;

import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper.MatrixVisitor;

public class StateMatrix extends ArrayMatrix<Set<Integer>> {

	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Set<Integer> newItem() {
		return new HashSet<Integer>();
	}

	@Override
	public String toString() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<Set<Integer>>(this).visitAllSquares(new MatrixVisitor<Set<Integer>>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, Set<Integer> states) {
				int sum = 0;
				for (Integer state : states) {
					sum += state;
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
