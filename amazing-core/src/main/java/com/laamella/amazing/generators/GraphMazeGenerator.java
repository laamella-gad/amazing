package com.laamella.amazing.generators;

import com.laamella.amazing.mazemodel.graph.Vertex;

/**
 * A GraphMazeGenerator generates a maze in a graph.
 */
public interface GraphMazeGenerator extends MazeGenerator {
	/**
	 * Generate a maze.
	 * @param entranceVertex the entrance of the maze
	 */
	void generateMaze(Vertex entranceVertex);
}
