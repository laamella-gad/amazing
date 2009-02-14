package com.laamella.daedalousy.solvers;

/**
 * This is like the blind alley filler, in that it also finds all possible
 * solutions by removing blind alleys from the Maze. However this just fills in
 * the stem passage of each blind alley, and doesn't touch any collection of
 * passages at the end of it. As a result this will create inaccessible passage
 * sections for cul-de-sacs or any blind alley more complicated than a dead end.
 * This algorithm focuses on the Maze, runs much faster than the blind alley
 * filler, although it requires extra memory. Assign each connected section of
 * walls to a unique set. To do this, for each wall section not already in a
 * set, flood across the top of the walls at that point, and assign all
 * reachable walls to a new set. After all walls are in sets, then for each
 * passage section, if the walls on either side of it are in the same set, then
 * seal off that passage. Such a passage must be a blind alley, since the walls
 * on either side of it link up with each other, forming a pen. Note a similar
 * technique can be used to help solve hypermazes, by sealing off space between
 * branches that connect with each other.
 */
public class BlindAlleySealerSolver {

}
