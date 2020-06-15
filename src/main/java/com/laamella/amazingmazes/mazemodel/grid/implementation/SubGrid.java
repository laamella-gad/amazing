package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;

import java.util.Set;

/**
 * Wraps a grid and presents itself as an subgrid. The graph representation is
 * flawed, since edges leading out of the subgrid still exist.
 */
public class SubGrid implements Grid {
    private final Grid delegateGrid;
    private final Position topLeft;
    private final Size size;

    public SubGrid(Grid delegateGrid, Position topLeft, Size size) {
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
