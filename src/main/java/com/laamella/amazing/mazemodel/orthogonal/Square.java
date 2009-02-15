package com.laamella.amazing.mazemodel.orthogonal;

public interface Square extends State {
	public static enum Direction {
		UP, DOWN, RIGHT, LEFT
	}

	Wall getWall(Direction direction);

//	Square getSquare(Direction direction);
}
