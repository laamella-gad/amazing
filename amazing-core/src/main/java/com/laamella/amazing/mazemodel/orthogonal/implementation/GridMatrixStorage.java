package com.laamella.amazing.mazemodel.orthogonal.implementation;

import static com.laamella.amazing.mazemodel.State.MazeDefinitionState.OPEN;

import java.util.Observable;
import java.util.Set;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.State;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.Matrix.UtilityWrapper;

//setChanged();
//notifyObservers();

public class GridMatrixStorage extends Observable implements GridStateStorage {
	private final Matrix.UtilityWrapper<Set<Object>> mazeMatrix;
	private final Size size;

	public GridMatrixStorage(final Matrix<Set<Object>> mazeMatrix) {
		this.mazeMatrix = new Matrix.UtilityWrapper<Set<Object>>(mazeMatrix);
		this.size = new Size((mazeMatrix.getSize().width - 1) / 2, (mazeMatrix.getSize().height - 1) / 2);
		this.mazeMatrix.visitAllSquares(new Matrix.UtilityWrapper.MatrixVisitor<Set<Object>>() {
			public void endRow() {
			}

			public void visit(Position position, Set<Object> value) {
				if (position.x % 2 == 1 && position.y % 2 == 1) {
					GridMatrixStorage.this.mazeMatrix.get(position).add(OPEN);
				}
			}

			public void startRow() {
			}
		});
	}

	private static class WallMatrixStorage implements WallStorage {
		private final Matrix.UtilityWrapper<Set<Object>> stateMatrix;
		private final Position position;

		public WallMatrixStorage(Matrix.UtilityWrapper<Set<Object>> stateMatrix, Position position) {
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

	public WallStorage getHorizontalWallState(Position position) {
		return new WallMatrixStorage(mazeMatrix, position.scale(2).move(1, 0));
	}

	public SquareStorage getSquareState(Position position) {
		return new SquareMatrixStorage(mazeMatrix, position.scale(2).move(1, 1));
	}

	public WallStorage createVerticalWallStorage(Position position) {
		return new WallMatrixStorage(mazeMatrix, position.scale(2).move(0, 1));
	}

	public Size getSize() {
		return size;
	}

	@Override
	public State getWallState(Position position, boolean horizontal) {
	}
}
