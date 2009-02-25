package com.laamella.amazing.generators.daedalus;

import com.laamella.amazing.generators.GraphMazeGenerator;
import com.laamella.amazing.mazemodel.graph.Graph;
import com.laamella.amazing.mazemodel.graph.Vertex;

/**
 * This is an improved version of the Aldous-Broder algorithm, in that it
 * produces Mazes exactly like that algorithm, with all possible Mazes generated
 * with equal probability, except that Wilson's algorithm runs much faster. It
 * requires storage up to the size of the Maze. Begin by making a random
 * starting cell part of the Maze. Proceed by picking a random cell not already
 * part of the Maze, and doing a random walk until a cell is found which is
 * already part of the Maze. Once the already created part of the Maze is hit,
 * go back to the random cell that was picked, and carve along the path that was
 * taken, adding those cells to the Maze. More specifically, when retracing the
 * path, at each cell carve along the direction that the random walk most
 * recently took when it left that cell. That avoids adding loops along the
 * retraced path, resulting in a single long passage being appended to the Maze.
 * The Maze is done when all cells have been appended to the Maze. This has
 * similar performance issues as Aldous-Broder, where it may take a long time
 * for the first random path to find the starting cell, however once a few paths
 * are in place, the rest of the Maze gets carved quickly. On average this runs
 * five times faster than Aldous-Broder, and takes less than twice as long as
 * the top algorithms. Note this runs twice as fast when implemented as a wall
 * adder, because the whole boundary wall starts as part of the Maze, so the
 * first walls are connected much quicker.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
public class WilsonMazeGenerator implements GraphMazeGenerator {

	@Override
	public void generateMaze(final Graph graph) {
		// TODO Auto-generated method stub

	}
}
