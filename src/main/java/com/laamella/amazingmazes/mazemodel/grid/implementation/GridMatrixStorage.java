package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Markable;
import com.laamella.amazingmazes.mazemodel.Markable.ObservableMarkable;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.PASSAGE;

public class GridMatrixStorage implements GridMarkerStorage {
    private final Matrix<ObservableMarkable> mazeMatrix;
    private final Size size;

    public GridMatrixStorage(Matrix<ObservableMarkable> mazeMatrix) {
        this.mazeMatrix = mazeMatrix;
        this.size = new Size((mazeMatrix.getSize().width - 1) / 2, (mazeMatrix.getSize().height - 1) / 2);
        this.mazeMatrix.visitAllSquares(new Matrix.Visitor<>() {
            @Override
            public void visit(Position position, ObservableMarkable value) {
                if (position.x % 2 == 1 && position.y % 2 == 1) {
                    GridMatrixStorage.this.mazeMatrix.get(position).mark(PASSAGE);
                }
            }
        });
    }

    @Override
    public Markable getMarkableSquare(Position position) {
        return mazeMatrix.get(position.scale(2).move(1, 1));
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public Markable getMarkableWall(Position position, boolean horizontal) {
        if (horizontal) {
            return mazeMatrix.get(position.scale(2).move(1, 0));
        }
        return mazeMatrix.get(position.scale(2).move(0, 1));
    }
}
