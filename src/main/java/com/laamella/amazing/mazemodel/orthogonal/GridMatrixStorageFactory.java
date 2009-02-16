package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.mazemodel.Matrix;
import com.laamella.amazing.mazemodel.MazeMatrix;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.MazeMatrix.State;

public class GridMatrixStorageFactory implements GridStorageFactory {
	private final Matrix<MazeMatrix.State> mazeMatrix;
	private final Matrix<Integer> stateMatrix;
	private final Size size;

	public GridMatrixStorageFactory(final Matrix<MazeMatrix.State> mazeMatrix, final Matrix<Integer> stateMatrix) {
		this.mazeMatrix = mazeMatrix;
		this.stateMatrix = stateMatrix;
		this.size = new Size(Math.min((mazeMatrix.getSize().width - 1) / 2, (stateMatrix.getSize().width - 1) / 2), Math.min(
				(mazeMatrix.getSize().height - 1) / 2, (stateMatrix.getSize().height - 1) / 2));
		new Matrix.UtilityWrapper<MazeMatrix.State>(mazeMatrix).visitAllSquares(new Matrix.UtilityWrapper.MatrixVisitor<MazeMatrix.State>() {
			public void endRow() {
			}

			public void visit(Position position, MazeMatrix.State value) {
				if (position.x % 2 == 0 && position.y % 2 == 0) {
					mazeMatrix.set(position, MazeMatrix.State.CLOSED);
				}
			}

			public void startRow() {
			}

		});
	}

	private static class WallMatrixStorage implements WallStorage {
		private final Matrix<MazeMatrix.State> mazeMatrix;
		private final Matrix<Integer> stateMatrix;
		private final Position position;

		public WallMatrixStorage(Matrix<MazeMatrix.State> mazeMatrix, Matrix<Integer> stateMatrix, Position position) {
			this.mazeMatrix = mazeMatrix;
			this.stateMatrix = stateMatrix;
			this.position = position;
		}

		public boolean isOpen() {
			return mazeMatrix.get(position) == MazeMatrix.State.OPEN;
		}

		public void setOpened(boolean opened) {
			mazeMatrix.set(position, opened ? MazeMatrix.State.OPEN : MazeMatrix.State.CLOSED);
		}

		public int getState() {
			return stateMatrix.get(position);
		}

		public void setState(int newState) {
			stateMatrix.set(position, newState);
		}
	}

	private static class SquareMatrixStorage implements SquareStorage {

		private final Matrix<Integer> stateMatrix;
		private final Position position;

		private SquareMatrixStorage(Matrix<Integer> stateMatrix, Position position) {
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
