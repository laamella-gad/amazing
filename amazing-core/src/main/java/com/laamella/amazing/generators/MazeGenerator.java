package com.laamella.amazing.generators;

public interface MazeGenerator {
	public static Object VISITED_WHILE_GENERATING = new Object();
	public static Object POSSIBLE_EXIT = new Object();

	void generateMaze();
}
