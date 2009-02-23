package com.laamella.amazing.generators;

public interface MazeGenerator {
	public enum GeneratorState {
		VISITED, POSSIBLE_EXIT;
	}

	void generateMaze();
}
