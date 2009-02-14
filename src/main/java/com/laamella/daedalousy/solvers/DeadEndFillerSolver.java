package com.laamella.daedalousy.solvers;

/**
 * This is a simple Maze solving algorithm. It focuses on the Maze, is always
 * very fast, and uses no extra memory. Just scan the Maze, and fill in each
 * dead end, filling in the passage backwards from the block until you reach a
 * junction. At the end only the solution will remain, or solutions if there are
 * more than one. This will always find the one unique solution for perfect
 * Mazes, but won't do much in heavily braid Mazes, and in fact won't do
 * anything useful at all for those Mazes without dead ends.
 */
public class DeadEndFillerSolver {

}
