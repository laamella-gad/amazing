package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.RowGenerator;
import com.laamella.amazingmazes.mazemodel.grid.Square;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GridRowGenerator implements RowGenerator {
    private final Grid grid;
    private int currentRow;

    public GridRowGenerator(final Grid grid) {
        this.grid = grid;
        currentRow = 0;
    }

    @Override
    public List<Square> nextRow() {
        final List<Square> row = new ArrayList<>();
        for (int x = 0; x < grid.getSize().width; x++) {
            row.add(grid.getSquare(new Position(x, currentRow)));
        }
        currentRow++;
        return row;
    }

    @Override
    public int rowsToGo() {
        return grid.getSize().height - currentRow;
    }

}
