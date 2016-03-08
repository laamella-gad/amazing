package com.laamella.amazingmazes.solvers;

/**
 * The Chain algorithm solves the Maze by effectively treating it as a number of
 * smaller Mazes, like links in a chain, and solving them in sequence. You have
 * to specify the start and desired end locations, and the algorithm will always
 * find a path from start to end if one exists, where the solution tends to be a
 * reasonably short if not the shortest solution. That means this can't solve
 * Mazes where you don't know exactly where the end is. This is most similar to
 * Pledge algorithm since it's also essentially a wall follower with a way to
 * jump between islands. Start by drawing a straight line (or at least a line
 * that doesn't double back on itself) from start to end, letting it cross walls
 * if needed. Then just follow the line from start to end. If you bump into a
 * wall, you can't go through it, so you have to go around. Send two wall
 * following "robots" in both directions along the wall you hit. If a robot runs
 * into the guiding line again, and at a point which is closer to the exit, then
 * stop, and follow that wall yourself until you get there too. Keep following
 * the line and repeating the process until the end is reached. If both robots
 * return to their original locations and directions, then farther points along
 * the line are inaccessible, and the Maze is unsolvable.
 */
public class ChainSolver {

}
