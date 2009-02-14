package com.laamella.daedalousy.mazemodel;

public interface Matrix {
	public static final int CLEAR = 0;
	public static final int SOLID = 1;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param value
	 *            0 = clear, 1 = solid
	 */
	void set(int x, int y, int value);

	int get(int x, int y);

	int getWidth();

	int getHeight();

	public static class MatrixUtilityWrapper implements Matrix {
		private final Matrix delegateMatrix;

		public MatrixUtilityWrapper(Matrix delegateMatrix) {
			this.delegateMatrix = delegateMatrix;
		}

		public int get(int x, int y) {
			return delegateMatrix.get(x, y);
		}

		public int getHeight() {
			return delegateMatrix.getHeight();
		}

		public int getWidth() {
			return delegateMatrix.getWidth();
		}

		public void set(int x, int y, int value) {
			delegateMatrix.set(x, y, value);
		}

		public interface MatrixVisitor {
			void visit(int x, int y, int value);

			void newRow();
		}

		public void visitAllSquares(final MatrixVisitor visitor) {
			for (int x = 0; x < delegateMatrix.getWidth(); x++) {
				for (int y = 0; y < delegateMatrix.getHeight(); y++) {
					visitor.visit(x, y, delegateMatrix.get(x, y));
				}
				visitor.newRow();
			}
		}

		@Override
		public String toString() {
			final StringBuffer maze = new StringBuffer();
			visitAllSquares(new MatrixVisitor() {
				public void newRow() {
					maze.append("\n");
				}

				public void visit(int x, int y, int value) {
					maze.append(toChar(value));
				}
			});
			return maze.toString();
		}

		private char toChar(int value) {
			switch (value) {
			case 0:
				return ' ';
			case 1:
				return '#';
			default:
				return (char) (value % 26 + 'A');
			}
		}

	}
}
