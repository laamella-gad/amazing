package com.laamella.amazing.solvers;

/**
 * Also called the "amoeba" solver, this method will find all shortest
 * solutions. It focuses on you multiple times, is fast for all types of Mazes,
 * and requires at least one copy of the Maze in memory in addition to using
 * memory up to the size of the Maze. It basically floods the Maze with "water",
 * such that all distances from the start are filled in at the same time (a
 * breadth first search in Computer Science terms) and whenever two
 * "columns of water" approach a passage from both ends (indicating a loop) add a
 * wall to the original Maze where they collide. Once all parts of the Maze have
 * been "flooded", fill in all the new dead ends, which can't be on the shortest
 * path, and repeat the process until no more collisions happen. (Picture amoebas
 * surfing at the crest of each "wave" as it flows down the passages, where when
 * waves collide, the amoebas head-butt and get knocked out, and form there a new
 * wall of unconscious amoebas, hence the name.)
 */
public class CollisionSolver {

}
