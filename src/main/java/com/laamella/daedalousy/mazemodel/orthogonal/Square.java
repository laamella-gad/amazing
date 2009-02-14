package com.laamella.daedalousy.mazemodel.orthogonal;

public interface Square extends State {
	public static enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	Wall getWall(Direction wall);
}
