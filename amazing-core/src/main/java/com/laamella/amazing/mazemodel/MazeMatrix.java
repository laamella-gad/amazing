package com.laamella.amazing.mazemodel;

import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper.MatrixVisitor;

public class MazeMatrix extends ArrayMatrix<MazeMatrix.WallState> {
	
	public static enum WallState{
		OPEN, CLOSED
	}

	public MazeMatrix(Size size) {
		super(size);
	}

	@Override
	protected WallState newItem() {
		return WallState.OPEN;
	}
	@Override
	public String toString() {
		final StringBuffer maze = new StringBuffer();
		new Matrix.UtilityWrapper<WallState>(this).visitAllSquares(new MatrixVisitor<WallState>() {
			public void endRow() {
				maze.append("-\n");
			}

			public void visit(Position position, WallState value) {
				maze.append(toChar(value));
			}

			public void startRow() {
				maze.append("-");
			}
		});
		return maze.toString();
	}

	private char toChar(WallState value) {
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
