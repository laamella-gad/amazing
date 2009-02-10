package com.laamella.daedalousy.solvers;

/**
 * This is a modified version of wall following that's able to jump between
 * islands, to solve Mazes wall following can't. It's a guaranteed way to reach
 * an exit on the outer edge of any 2D Maze from any point in the middle, however
 * it's not able to do the reverse, i.e. find a solution within the Maze. It's
 * great for implementation by a Maze escaping robot, since it can get out of any
 * Maze without having to mark or remember the path in any way. Start by picking
 * a direction, and always move in that direction when possible. When a wall is
 * hit, start wall following until your chosen direction is available again. Note
 * you should start wall following upon the far wall that's hit, where if the
 * passage turns a corner there, it can cause you to turn around in the middle of
 * a passage and go back the way you came. When wall following, count the number
 * of turns you make, e.g. a left turn is -1 and a right turn is 1. Only stop
 * wall following and take your chosen direction when the total number of turns
 * you've made is 0, i.e. if you've turned around 360 degrees or more, keep wall
 * following until you untwist yourself. The counting ensures you're eventually
 * able to reach the far side of the island you're currently on, and jump to the
 * next island in your chosen direction, where you'll keep on island hopping in
 * that direction until you hit the boundary wall, at which point wall following
 * takes you to the exit. Note Pledge algorithm may make you visit a passage or
 * the start more than once, although subsequent times will always be with
 * different turn totals. Without marking your path, the only way to know whether
 * the Maze is unsolvable is if your turn total keeps increasing, although the
 * turn total can get to large numbers in solvable Mazes in a spiral passage.
 */
public class Pledge {

}
