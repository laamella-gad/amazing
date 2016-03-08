package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;

public interface Square extends Vertex {
	Wall getWall(Direction direction);

	Square getSquare(Direction direction);

	Position getPosition();
}
