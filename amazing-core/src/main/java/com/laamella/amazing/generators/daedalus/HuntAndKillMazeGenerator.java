package com.laamella.amazing.generators.daedalus;

import com.laamella.amazing.generators.GraphMazeGenerator;
import com.laamella.amazing.mazemodel.graph.Graph;

/**
 * This algorithm is nice because it requires no extra storage or stack, and is
 * therefore suited to creating the largest Mazes or Mazes on the most limited
 * systems, since there are no issues of running out of memory. Since there are
 * no rules that must be followed all the time, it's also the easiest to modify
 * and to get to create Mazes of different textures. It's most similar to the
 * recursive backtracker, except when there's no unmade cell next to the current
 * position, you enter "hunting" mode, and systematically scan over the Maze
 * until an unmade cell is found next to an already carved into cell, at which
 * point you start carving again at that new location. The Maze is done when all
 * cells have been scanned over once in "hunt" mode. This algorithm tends to
 * make Mazes with a high "river" factor, but not as high as the recursive
 * backtracker. You can make this generate Mazes with a lower river factor by
 * choosing to enter "hunt" mode more often. It runs slower due to the time
 * spent hunting for the last cells, but isn't much slower than Kruskal's
 * algorithm. This can be done as a wall adder if you randomly teleport on
 * occasion, to avoid the issues the recursive backtracker has.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/Maze.java">Some example code</a>
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
public class HuntAndKillMazeGenerator implements GraphMazeGenerator {

	@Override
	public void generateMaze(final Graph graph) {
		// TODO Hunt and kill algorithm

	}
}
