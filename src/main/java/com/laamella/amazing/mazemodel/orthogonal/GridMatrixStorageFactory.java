package com.laamella.amazing.mazemodel.orthogonal;

import java.util.Set;

import com.laamella.amazing.mazemodel.Matrix;
import com.laamella.amazing.mazemodel.MazeMatrix;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.Matrix.UtilityWrapper;

public class GridMatrixStorageFactory implements GridStorageFactory {
	private final Matrix<MazeMatrix.WallState> mazeMatrix;
	private final UtilityWrapper<Set<Object>> stateMatrix;
	private final Size size;

	public GridMatrixStorageFactory(final Matrix<MazeMatrix.WallState> mazeMatrix, final UtilityWrapper<Set<Object>> stateMatrix) {
		this.mazeMatrix = mazeMatrix;
		this.stateMatrix = stateMatrix;
		this.size = new Size(Math.min((mazeMatrix.getSize().width - 1) / 2, (stateMatrix.getSize().width - 1) / 2), Math.min(
				(mazeMatrix.getSize().height - 1) / 2, (stateMatrix.getSize().height - 1) / 2));
		new Matrix.UtilityWrapper<MazeMatrix.WallState>(mazeMatrix).visitAllSquares(new Matrix.UtilityWrapper.MatrixVisitor<MazeMatrix.WallState>() {
			public void endRow() {
			}

			public void visit(Position position, MazeMatrix.WallState value) {
				if (position.x % 2 == 0 && position.y % 2 == 0) {
					mazeMatrix.set(position, MazeMatrix.WallState.CLOSED);
				}
			}

			public void startRow() {
			}

		});
	}

	private static class WallMatrixStorage implements WallStorage {
		private final Matrix<MazeMatrix.WallState> mazeMatrix;
		private final Matrix<Set<Object>> stateMatrix;
		private final Position position;

		public WallMatrixStorage(Matrix<MazeMatrix.WallState> mazeMatrix, UtilityWrapper<Set<Object>> stateMatrix, Position position) {
			this.mazeMatrix = mazeMatrix;
			this.stateMatrix = stateMatrix;
			this.position = position;
		}

		public boolean isOpen() {
			return mazeMatrix.get(position) == MazeMatrix.WallState.OPEN;
		}

		public void setOpened(boolean opened) {
			mazeMatrix.set(position, opened ? MazeMatrix.WallState.OPEN : MazeMatrix.WallState.CLOSED);
		}

		public boolean hasState(Object state) {
			return stateMatrix.get(position).contains(state);
		}

		public void setState(Object newState, boolean hasOrNot) {
			if (hasOrNot) {
				stateMatrix.get(position).add(newState);
			} else {
				stateMatrix.get(position).remove(newState);
			}
		}

	}

	private static class SquareMatrixStorage implements SquareStorage {
		private final Matrix<Set<Object>> stateMatrix;
		private final Position position;

		private SquareMatrixStorage(UtilityWrapper<Set<Object>> stateMatrix, Position position) {
			this.stateMatrix = stateMatrix;
			this.position = position;
		}

		public boolean hasState(Object state) {
			return stateMatrix.get(position).contains(state);
		}

		public void setState(Object newState, boolean hasOrNot) {
			if (hasOrNot) {
				stateMatrix.get(position).add(newState);
			} else {
				stateMatrix.get(position).remove(newState);
			}
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
