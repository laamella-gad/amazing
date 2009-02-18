package com.laamella.amazing.generators;

/**
 * This requires storage proportional to the size of the Maze. During creation,
 * each cell is one of three types: (1) "In": The cell is part of the Maze and
 * has been carved into already, (2) "Frontier": The cell is not part of the Maze
 * and has not been carved into yet, but is next to a cell that's already "in",
 * and (3) "Out": The cell is not part of the Maze yet, and none of its neighbors
 * are "in" either. Start by picking a cell, making it "in", and setting all its
 * neighbors to "frontier". Proceed by picking a "frontier" cell at random, and
 * carving into it from one of its neighbor cells that are "in". Change that
 * "frontier" cell to "in", and update any of its neighbors that are "out" to
 * "frontier". The Maze is done when there are no more "frontier" cells left
 * (which means there are no more "out" cells left either, so they're all "in").
 * This algorithm results in Mazes with a very low "river" factor, with many
 * short dead ends, and the solution is usually pretty direct as well. It also
 * runs very fast when implemented right, with only Eller's algorithm being
 * faster.
 */
public class PrimMazeGenerator implements MazeGenerator{
	public void generateMaze() {
		// TODO Auto-generated method stub
		
	}
}
