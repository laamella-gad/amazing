package com.laamella.daedalousy.solvers;

/**
 * As the name indicates, this algorithm finds the shortest solution, picking
 * one if there are multiple shortest solutions. It focuses on you multiple
 * times, is fast for all types of Mazes, and requires quite a bit of extra
 * memory proportional to the size of the Maze. Like the collision solver, this
 * basically floods the Maze with "water", such that all distances from the start
 * are filled in at the same time (a breadth first search in Computer Science
 * terms) however each "drop" or pixel remembers which pixel it was filled in by.
 * Once the solution is hit by a "drop", trace backwards from it to the beginning
 * and that's a shortest path. This algorithm works well given any input, because
 * unlike most of the others, this doesn't require the Maze to have any one pixel
 * wide passages that can be followed. Note this is basically the A* path finding
 * algorithm without a heuristic so all movement is given equal weight.
 */
public class ShortestPathFinder {

}
