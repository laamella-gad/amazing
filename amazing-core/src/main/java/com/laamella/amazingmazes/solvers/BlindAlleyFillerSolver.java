package com.laamella.amazingmazes.solvers;

/**
 * This method finds all possible solutions, regardless of how long or short
 * they may be. It does so by filling in all blind alleys, where a blind alley
 * is a passage where if you walk down it in one direction, you will have to
 * backtrack through that passage in the other direction in order to reach the
 * goal. All dead ends are blind alleys, and all nooses as described in the
 * cul-de-sac filler are as well, along with any sized section of passages
 * connected to the rest of the Maze by only a single stem. This algorithm
 * focuses on the Maze, uses no extra memory, but unfortunately is rather slow.
 * For each junction, send a wall following robot down each passage from it, and
 * see if the robot sent down a path comes back from the same path (as opposed
 * to returning from a different direction, or it exiting the Maze). If it does,
 * then that passage and everything down it can't be on any solution path, so
 * seal that passage off and fill in everything behind it. This algorithm will
 * fill in everything the cul-de-sac filler will and then some, however the
 * collision solver will fill in everything this algorithm will and then some.
 */
public class BlindAlleyFillerSolver {

}
