package com.laamella.amazing.mazemodel;

import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper.MatrixVisitor;

public class StateMatrix extends ArrayMatrix<Integer> {

	public StateMatrix(Size size) {
		super(size);
	}

	@Override
	protected Integer newItem() {
		return 0;
	}

	@Override
	public String toString() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<Integer>(this).visitAllSquares(new MatrixVisitor<Integer>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, Integer value) {
				maze.append(toChar(value));
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
