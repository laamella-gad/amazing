package com.laamella.amazing.generators.perfect;

import com.laamella.amazing.generators.MazeGenerator;

/**
 * This algorithm is interesting because it doesn't "grow" the Maze like a tree,
 * but rather carves passage segments all over the Maze at random, but yet still
 * results in a perfect Maze in the end. It requires storage proportional to the
 * size of the Maze, along with the ability to enumerate each edge or wall
 * between cells in the Maze in random order (which usually means creating a list
 * of all edges and shuffling it randomly). Label each cell with a unique id,
 * then loop over all the edges in random order. For each edge, if the cells on
 * either side of it have different id's, then erase the wall, and set all the
 * cells on one side to have the same id as those on the other. If the cells on
 * either side of the wall already have the same id, then there already exists
 * some path between those two cells, so the wall is left alone so as to not
 * create a loop. This algorithm yields Mazes with a low "river" factor, but not
 * as low as Prim's algorithm. Merging the two sets on either side of the wall
 * will be a slow operation if each cell just has a number and are merged by a
 * loop. Merging as well as lookup can be done in near constant time by giving
 * each cell a node in a tree structure, with the id at the root, where merging
 * is done quickly by splicing the trees together. Done right, this algorithm
 * runs reasonably fast, but not as fast as either of the above two, because of
 * the edge list and set management.
 */
public class KruskalMazeGenerator implements MazeGenerator{
	public void generateMaze() {
		// TODO Auto-generated method stub
		
	}

}
