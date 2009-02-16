package com.laamella.amazing.mazemodel;

import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper.MatrixVisitor;

public class MazeMatrix extends ArrayMatrix<MazeMatrix.State> {
	
	public static enum State{
		OPEN, CLOSED
	}

	public MazeMatrix(Size size) {
		super(size);
	}

	@Override
	protected State newItem() {
		return State.OPEN;
	}
	@Override
	public String toString() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<State>(this).visitAllSquares(new MatrixVisitor<State>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, State value) {
				maze.append(toChar(value));
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	private char toChar(State value) {
		switch (value) {
		case OPEN:
			return ' ';
		case CLOSED:
			return '#';
		default:
			throw new IllegalStateException();
		}
	}
}
