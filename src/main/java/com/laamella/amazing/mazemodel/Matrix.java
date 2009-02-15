package com.laamella.amazing.mazemodel;

public interface Matrix {
	public static final int OPENED = 0;
	public static final int CLOSED = 1;

	void set(Position position, int value);

	int get(Position position);

	Size getSize();

	public static class MatrixUtilityWrapper implements Matrix {
		private final Matrix delegateMatrix;

		public MatrixUtilityWrapper(Matrix delegateMatrix) {
			this.delegateMatrix = delegateMatrix;
		}

		public interface MatrixVisitor {
			void visit(Position position, int value);

			void newRow();
		}

		public void visitAllSquares(final MatrixVisitor visitor) {
			for (int y = 0; y < delegateMatrix.getSize().height; y++) {
				for (int x = 0; x < delegateMatrix.getSize().width; x++) {
					final Position position = new Position(x, y);
					visitor.visit(position, delegateMatrix.get(position));
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

				public void visit(Position position, int value) {
					maze.append(toChar(value));
				}
			});
			return maze.toString();
		}

		private char toChar(int value) {
			switch (value) {
			case 0:
				return '.';
			case 1:
				return '#';
			default:
				return (char) (value % 26 + 'A');
			}
		}

		public int get(Position position) {
			return delegateMatrix.get(position);
		}

		public Size getSize() {
			return delegateMatrix.getSize();
		}

		public void set(Position position, int value) {
			delegateMatrix.set(position, value);
		}

	}
}
