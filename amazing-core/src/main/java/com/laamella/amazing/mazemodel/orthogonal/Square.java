package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.mazemodel.Position;

public interface Square extends State {
	Wall getWall(Direction direction);

	Square getSquare(Direction direction);

	Position getPosition();
}
