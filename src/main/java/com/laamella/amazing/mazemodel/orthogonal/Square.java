package com.laamella.amazing.mazemodel.orthogonal;

public interface Square extends State {
	Wall getWall(Direction direction);

	Square getSquare(Direction direction);
}
