package com.laamella.amazing.mazemodel.grid;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.graph.Vertex;

public interface Square extends Vertex {
	Wall getWall(Direction direction);

	Square getSquare(Direction direction);

	Position getPosition();
}
