package com.laamella.amazing.mazemodel.grid.implementation;

import java.util.Set;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.graph.Edge;
import com.laamella.amazing.mazemodel.graph.Vertex;
import com.laamella.amazing.mazemodel.grid.Grid;
import com.laamella.amazing.mazemodel.grid.Square;

/**
 * Wraps a grid and presents itself as an subgrid. The graph representation is
 * flawed, since edges leading out of the subgrid still exist.
 */
public class SubGrid implements Grid {
	private final Grid delegateGrid;
	private final Position topLeft;
	private final Size size;

	public SubGrid(final Grid delegateGrid, final Position topLeft, final Size size) {
		this.delegateGrid = delegateGrid;
		this.topLeft = topLeft;
		this.size = size;
	}

	@Override
	public Size getSize() {
		return size;
	}

	@Override
	public Square getSquare(Position position) {
		return delegateGrid.getSquare(position.move(topLeft));
	}

	@Override
	public Set<Edge> getEdges() {
		return delegateGrid.getEdges();
	}

	@Override
	public Set<Vertex> getVertices() {
		return delegateGrid.getVertices();
	}

}
