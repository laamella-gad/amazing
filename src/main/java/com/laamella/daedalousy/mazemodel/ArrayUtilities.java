package com.laamella.daedalousy.mazemodel;


public class ArrayUtilities {
	public static interface Visitor2dArray {
		void visit(int x, int y);
	}

	public static abstract class Visitor2dArrayWithNewRow implements Visitor2dArray {
		private int row = 0;

		public final void visit(int x, int y) {
			visit_(x, y);
			if (row != y) {
				newRow();
				row = y;
			}
		}

		public abstract void visit_(int x, int y);

		public abstract void newRow();

	}

	public static <T> void visit2dArray(T[][] array, Visitor2dArray visitor) {
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[x].length; y++) {
				visitor.visit(x, y);
			}
		}
	}

	public static void visit2dArray(int[][] array, Visitor2dArray visitor) {
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[x].length; y++) {
				visitor.visit(x, y);
			}
		}
	}


}
