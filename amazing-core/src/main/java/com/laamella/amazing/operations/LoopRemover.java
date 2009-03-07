package com.laamella.amazing.operations;

/**
 * This means to edit the Maze such that there are no loops or detached walls
 * within it, every section of the Maze reachable from any other by at most one
 * path. The way to do this is almost identical to the isolation remover, just
 * treat walls as passages and vice versa. Start with a copy of the Maze, then
 * flood across the top of the outer walls. Scan the Maze (preferably in a
 * random other that still hits every possible wall vertex) for any unfilled
 * walls adjacent to a filled wall. Add a wall segment to the original Maze at
 * that point connecting the two wall sections, flood the Maze at this new
 * point, and repeat until every section is filled. This utility is used in the
 * creation of template Mazes, and can be used to convert a braid Maze to a
 * perfect Maze that still looks similar to the original.
 */
public class LoopRemover {

}
