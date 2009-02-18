package com.laamella.amazing.operations;

/**
 * This means to find those passages or intersection points in a Maze such that
 * every solution to that Maze passes through them. To do this, run the left
 * hand wall follower to get the leftmost solution, and run the right hand wall
 * follower to get the rightmost solution, where places the two solutions have
 * in common are the bottlenecks. That technique however only works for Mazes
 * that wall following will successfully solve. For other Mazes, to find
 * bottleneck passages, find any solution to the Maze, and also run the blind
 * alley sealer (which may make the Maze unsolvable if it treats an entrance or
 * exit within the Maze as a large blind alley). Parts of the solution path that
 * go through sealed off passages, are bottlenecks.
 */
public class BottleneckFinderOperation {

}
