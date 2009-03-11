package com.laamella.amazingmazes.mazemodel.matrix;

import com.laamella.amazingmazes.mazemodel.Position;

public class ArrayUtilities {
	public static interface Visitor2dArray {
		void visit(Position position);
	}

	public static abstract class Visitor2dArrayWithNewRow implements Visitor2dArray {
		private int row = 0;

		public final void visit(Position position) {
			visit_(position);
			if (row != position.y) {
				newRow();
				row = position.y;
			}
		}

		public abstract void visit_(Position position);

		public abstract void newRow();

	}

	public static <T> void visit2dArray(T[][] array, Visitor2dArray visitor) {
		for (int y = 0; y < array[0].length; y++) {
			for (int x = 0; x < array.length; x++) {
				visitor.visit(new Position(x, y));
			}
		}
	}

	public static void visit2dArray(int[][] array, Visitor2dArray visitor) {
		for (int y = 0; y < array[0].length; y++) {
			for (int x = 0; x < array.length; x++) {
				visitor.visit(new Position(x, y));
			}
		}
	}

}
