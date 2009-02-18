package com.laamella.amazing.mazemodel;

import java.util.Observable;

public interface Matrix<T> {
	public static final Integer OPENED = 0;
	public static final Integer CLOSED = 1;

	void set(Position position, T value);

	T get(Position position);

	Size getSize();

	public static class UtilityWrapper<T> extends Observable implements Matrix<T> {
		private final Matrix<T> delegateMatrix;

		public UtilityWrapper(Matrix<T> delegateMatrix) {
			this.delegateMatrix = delegateMatrix;
		}

		public interface MatrixVisitor<T> {
			void visit(Position position, T value);

			void endRow();

			void startRow();
		}

		public void visitAllSquares(final MatrixVisitor<T> visitor) {
			for (int y = 0; y < delegateMatrix.getSize().height; y++) {
				visitor.startRow();
				for (int x = 0; x < delegateMatrix.getSize().width; x++) {
					final Position position = new Position(x, y);
					visitor.visit(position, delegateMatrix.get(position));
				}
				visitor.endRow();
			}
		}


		public T get(Position position) {
			return delegateMatrix.get(position);
		}

		public Size getSize() {
			return delegateMatrix.getSize();
		}

		public void set(Position position, T value) {
			delegateMatrix.set(position, value);
			setChanged();
			notifyObservers();
		}

		@Override
		public String toString() {
			return delegateMatrix.toString();
		}
	}
}
