package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.mazemodel.Matrix;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;

public class GridMatrixStorageFactory implements GridStorageFactory {
	private final Matrix mazeMatrix;
	private final Matrix stateMatrix;
	private final Size size;

	public GridMatrixStorageFactory(final Matrix mazeMatrix, final Matrix stateMatrix) {
		this.mazeMatrix = mazeMatrix;
		this.stateMatrix = stateMatrix;
		this.size = new Size(Math.min((mazeMatrix.getSize().width - 1) / 2, (stateMatrix.getSize().width - 1) / 2), Math.min(
				(mazeMatrix.getSize().height - 1) / 2, (stateMatrix.getSize().height - 1) / 2));
		new Matrix.MatrixUtilityWrapper(mazeMatrix).visitAllSquares(new Matrix.MatrixUtilityWrapper.MatrixVisitor() {
			public void newRow() {
			}

			public void visit(Position position, int value) {
				if (position.x % 2 == 0 && position.y % 2 == 0) {
					mazeMatrix.set(position, Matrix.CLOSED);
				}
			}
		});
	}

	private static class WallMatrixStorage implements WallStorage {
		private final Matrix mazeMatrix;
		private final Matrix stateMatrix;
		private final Position position;

		public WallMatrixStorage(Matrix mazeMatrix, Matrix stateMatrix, Position position) {
			this.mazeMatrix = mazeMatrix;
			this.stateMatrix = stateMatrix;
			this.position = position;
		}

		public boolean isOpen() {
			return mazeMatrix.get(position) == Matrix.OPENED;
		}

		public void setOpened(boolean opened) {
			mazeMatrix.set(position, opened ? Matrix.OPENED : Matrix.CLOSED);
		}

		public int getState() {
			return stateMatrix.get(position);
		}

		public void setState(int newState) {
			stateMatrix.set(position, newState);
		}
	}

	private static class SquareMatrixStorage implements SquareStorage {

		private final Matrix stateMatrix;
		private final Position position;

		private SquareMatrixStorage(Matrix stateMatrix, Position position) {
			this.stateMatrix = stateMatrix;
			this.position = position;
		}

		public int getState() {
			return stateMatrix.get(position);
		}

		public void setState(int newState) {
			stateMatrix.set(position, newState);
		}

	}

	public WallStorage createHorizontalWallStorage(Position position) {
		return new WallMatrixStorage(mazeMatrix, stateMatrix, position.scale(2).move(1, 0));
	}

	public SquareStorage createSquareStorage(Position position) {
		return new SquareMatrixStorage(stateMatrix, position.scale(2).move(1, 1));
	}

	public WallStorage createVerticalWallStorage(Position position) {
		return new WallMatrixStorage(mazeMatrix, stateMatrix, position.scale(2).move(0, 1));
	}

	public Size getSize() {
		return size;
	}
}
