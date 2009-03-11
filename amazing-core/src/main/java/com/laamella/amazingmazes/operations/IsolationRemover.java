package com.laamella.amazingmazes.operations;

/**
 * This means to edit the Maze such that there are no passage sections that are
 * inaccessible from the rest of the Maze, by removing walls to connect such
 * sections to the rest of the Maze. Start with a copy of the Maze, then flood
 * the passage at the beginning. Scan the Maze (preferably in a random order
 * that still hits every possible cell) for any unfilled cells adjacent to a
 * filled cell. Remove a wall segment in the original Maze at that point, flood
 * the Maze at this new point, and repeat until every section is filled. This
 * utility is used in the creation of braid and template Mazes.
 */
public class IsolationRemover {

}
