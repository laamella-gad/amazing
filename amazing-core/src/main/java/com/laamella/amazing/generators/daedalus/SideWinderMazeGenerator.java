package com.laamella.amazing.generators.daedalus;

import com.laamella.amazing.generators.GridMazeGenerator;
import com.laamella.amazing.mazemodel.orthogonal.Grid;

/**
 * This simple algorithm is very similar to the binary tree algorithm, and only
 * slightly more complicated. The Maze is generated one row at a time: For each
 * cell randomly decide whether to carve a passage leading right. If a passage
 * is not carved, then consider the horizontal passage just completed, formed by
 * the current cell and any cells to the left that carved passages leading to
 * it. Randomly pick one cell along this passage, and carve a passage leading up
 * from it (which must be the current cell if the adjacent cell didn't carve).
 * While a binary tree Maze always goes up from the leftmost cell of a
 * horizontal passage, a sidewinder Maze goes up from a random cell. While
 * binary tree has the top and left edges of the Maze one long passage, a
 * sidewinder Maze has just the top edge one long passage. Like binary tree, a
 * sidewinder Maze can be solved deterministically without error from bottom to
 * top, because at each row, there will always be exactly one passage leading
 * up. A solution to a sidewinder Maze will never double back on itself or visit
 * a row more than once, although it will "wind from side to side". The only
 * cell type that can't exist in a sidewinder Maze is a dead end with the
 * passage facing down, because that would contradict the fact that every
 * passage going up leads back to the start. A sidewinder Maze tends to have an
 * elitist solution, where the right path is very direct, but there are many
 * long false paths leading down from the top next to it.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
public class SideWinderMazeGenerator implements GridMazeGenerator {
	@Override
	public void generateMaze(Grid grid) {
		// TODO Auto-generated method stub
		
	}
}
