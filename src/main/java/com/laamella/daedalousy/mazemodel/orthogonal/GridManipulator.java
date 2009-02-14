package com.laamella.daedalousy.mazemodel.orthogonal;

import com.laamella.daedalousy.mazemodel.orthogonal.Square.Direction;

public class GridManipulator {

	public static interface SquareVisitor {
		void visitSquare(int x, int y, Square square);
		void newRow();
	}

	public static void closeAllWalls(final Grid grid) {
		forAllCells(grid, new SquareVisitor() {
			public void visitSquare(int x, int y, Square square) {
				square.getWall(Direction.NORTH).setSolid(true);
				square.getWall(Direction.EAST).setSolid(true);
				square.getWall(Direction.SOUTH).setSolid(true);
				square.getWall(Direction.WEST).setSolid(true);

			}

			public void newRow() {
			}
		});
	}

	public static void forAllCells(final Grid grid, final SquareVisitor visitor) {
		for (int x = 0; x < grid.getWidthInSquares(); x++) {
			for (int y = 0; y < grid.getHeightInSquares(); y++) {
				visitor.visitSquare(x, y, grid.getSquare(x, y));
			}
			visitor.newRow();
		}
	}

	public static interface Visitor2dArray {
		void visit(int x, int y);
		void newRow();
	}

	public static <T> void visit2dArray(T[][] array, Visitor2dArray visitor) {
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[x].length; y++) {
				visitor.visit(x, y);
			}
			visitor.newRow();
		}
	}
	public static void visit2dArray(int[][] array, Visitor2dArray visitor) {
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[x].length; y++) {
				visitor.visit(x, y);
			}
			visitor.newRow();
		}
	}

}
