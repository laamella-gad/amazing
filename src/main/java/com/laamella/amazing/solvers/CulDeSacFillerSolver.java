package com.laamella.amazing.solvers;

/**
 * This method finds and fills in cul-de-sacs or nooses, i.e. constructs in a
 * Maze consisting of a blind alley stem that has a single loop at the end. Like
 * the dead end filler, it focuses on the Maze, is always fast, and uses no extra
 * memory. Scan the Maze, and for each noose junction (a noose junction being one
 * where two of the passages leading from it connect with each other with no
 * other junctions along the way) add a wall to convert the entire noose to a
 * long dead end. Afterwards run the dead end filler. Mazes can have nooses
 * hanging off other constructs that will become nooses once the first one is
 * removed, so the whole process can be repeated until nothing happens during a
 * scan. This doesn't do much in complicated heavily braid Mazes, but will be
 * able to invalidate more than just the dead end filler.
 */
public class CulDeSacFillerSolver {

}
