package com.laamella.amazingmazes.solvers;

/**
 * This is another simple Maze solving algorithm. It focuses on you, is always
 * very fast, and uses no extra memory. Start following passages, and whenever
 * you reach a junction always turn right (or left). Equivalent to a human
 * solving a Maze by putting their hand on the right (or left) wall and leaving
 * it there as they walk through. If you like you can mark what cells you've
 * visited, and what cells you've visited twice, where at the end you can retrace
 * the solution by following those cells visited once. This method won't
 * necessarily find the shortest solution, and it doesn't work at all when the
 * goal is in the center of the Maze and there's a closed circuit surrounding it,
 * as you'll go around the center and eventually find yourself back at the
 * beginning. Wall following can be done in a deterministic way in a 3D Maze by
 * projecting the 3D passages onto the 2D plane, e.g. by pretending up passages
 * actually lead northwest and down lead southeast, and then applying normal wall
 * following rules.
 */
public class WallFollowerSolver {

}
