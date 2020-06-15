package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Stateful;
import com.laamella.amazingmazes.mazemodel.Stateful.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.PASSAGE;

public class GridMatrixStorage implements GridStateStorage {
    private final Matrix<ObservableObjectSetState> mazeMatrix;
    private final Size size;

    public GridMatrixStorage(Matrix<ObservableObjectSetState> mazeMatrix) {
        this.mazeMatrix = mazeMatrix;
        this.size = new Size((mazeMatrix.getSize().width - 1) / 2, (mazeMatrix.getSize().height - 1) / 2);
        this.mazeMatrix.visitAllSquares(new Matrix.Visitor<>() {
            @Override
            public void visit(Position position, ObservableObjectSetState value) {
                if (position.x % 2 == 1 && position.y % 2 == 1) {
                    GridMatrixStorage.this.mazeMatrix.get(position).setState(PASSAGE, true);
                }
            }
        });
    }

    @Override
    public Stateful getSquareState(Position position) {
        return mazeMatrix.get(position.scale(2).move(1, 1));
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public Stateful getWallState(Position position, boolean horizontal) {
        if (horizontal) {
            return mazeMatrix.get(position.scale(2).move(1, 0));
        }
        return mazeMatrix.get(position.scale(2).move(0, 1));
    }
}
