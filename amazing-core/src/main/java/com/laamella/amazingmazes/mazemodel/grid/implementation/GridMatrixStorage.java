package com.laamella.amazingmazes.mazemodel.grid.implementation;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;

import com.laamella.amazingmazes.mazemodel.*;
import com.laamella.amazingmazes.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;

public class GridMatrixStorage implements GridStateStorage {
	private final Matrix.UtilityWrapper<ObservableObjectSetState> mazeMatrix;
	private final Size size;

	public GridMatrixStorage(final Matrix<ObservableObjectSetState> mazeMatrix) {
		this.mazeMatrix = new Matrix.UtilityWrapper<ObservableObjectSetState>(mazeMatrix);
		this.size = new Size((mazeMatrix.getSize().width - 1) / 2, (mazeMatrix.getSize().height - 1) / 2);
		this.mazeMatrix.visitAllSquares(new Matrix.UtilityWrapper.MatrixVisitor<ObservableObjectSetState>() {
			public void endRow() {
			}

			public void visit(Position position, ObservableObjectSetState value) {
				if (position.x % 2 == 1 && position.y % 2 == 1) {
					GridMatrixStorage.this.mazeMatrix.get(position).setState(PASSAGE, true);
				}
			}

			public void startRow() {
			}
		});
	}

	public State getSquareState(Position position) {
		return mazeMatrix.get(position.scale(2).move(1, 1));
	}

	public Size getSize() {
		return size;
	}

	@Override
	public State getWallState(Position position, boolean horizontal) {
		if (horizontal) {
			return mazeMatrix.get(position.scale(2).move(1,0));
		}
		return mazeMatrix.get(position.scale(2).move(0,1));
	}
}
