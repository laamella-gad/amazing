package com.laamella.daedalousy.generators;

/**
 * Another simple algorithm for rectangular mazes, recursive division, works as
 * follows. Begin with a rectangular space with no walls. Call this a chamber.
 * Build at random points two walls that are perpendicular to each other. These
 * two walls divide the large chamber into four smaller chambers separated by
 * four walls. Choose three of the four walls at random, and open a one
 * cell-wide hole at a random point in each of the three. Continuing in this
 * manner recursively, until every chamber has a width of one cell in either of
 * the two directions, one can easily generate interesting mazes that avoid the
 * ease of solution of binary tree mazes.
 * <p>Source: http://en.wikipedia.org/wiki/Maze_generation_algorithm
 */
public class RecursiveDivisionMazeGenerator {

}
