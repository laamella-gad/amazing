package com.laamella.amazingmazes.mazemodel.grid.implementation;

import java.util.ArrayList;
import java.util.List;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.*;

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
		final List<Square> row = new ArrayList<Square>();
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
