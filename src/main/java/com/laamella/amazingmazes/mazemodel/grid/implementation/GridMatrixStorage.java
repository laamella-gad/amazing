package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.State;
import com.laamella.amazingmazes.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.PASSAGE;

public class GridMatrixStorage implements GridStateStorage {
    private final Matrix<ObservableObjectSetState> mazeMatrix;
    private final Size size;

    public GridMatrixStorage(final Matrix<ObservableObjectSetState> mazeMatrix) {
        this.mazeMatrix = mazeMatrix;
        this.size = new Size((mazeMatrix.getSize().width - 1) / 2, (mazeMatrix.getSize().height - 1) / 2);
        this.mazeMatrix.visitAllSquares(new Matrix.Visitor<>() {
            @Override
            public void endRow() {
            }

            @Override
            public void visit(final Position position, final ObservableObjectSetState value) {
                if (position.x % 2 == 1 && position.y % 2 == 1) {
                    GridMatrixStorage.this.mazeMatrix.get(position).setState(PASSAGE, true);
                }
            }

            @Override
            public void startRow() {
            }
        });
    }

    @Override
    public State getSquareState(final Position position) {
        return mazeMatrix.get(position.scale(2).move(1, 1));
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public State getWallState(final Position position, final boolean horizontal) {
        if (horizontal) {
            return mazeMatrix.get(position.scale(2).move(1, 0));
        }
        return mazeMatrix.get(position.scale(2).move(0, 1));
    }
}
