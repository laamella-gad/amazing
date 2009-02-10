package com.laamella.daedalousy.generators;

/**
 * This is basically the simplest and fastest algorithm possible, however Mazes
 * produced by it have a very biased texture. For each cell carve a passage
 * either leading up or leading left, but not both. In the wall added version,
 * for each vertex add a wall segment leading down or right, but not both. Each
 * cell is independent of every other cell, where you don't have to refer to the
 * state of any other cells when creating it. Hence this is a true memoryless
 * Maze generation algorithm, with no limit to the size of Maze you can create.
 * This is basically a computer science binary tree, if you consider the upper
 * left corner the root, where each node or cell has one unique parent which is
 * the cell above or to the left of it. Binary tree Mazes are different than
 * standard perfect Mazes, since about half the cell types can never exist in
 * them. For example there will never be a crossroads, and all dead ends have
 * passages pointing up or left, and never down or right. The Maze tends to have
 * passages leading diagonally from upper left to lower right, where the Maze is
 * much easier to navigate from lower right to upper left. You will always be
 * able to travel up or left, but never both, so you can always deterministically
 * travel diagonally up and to the left without hitting any barriers. Traveling
 * down and to the right is when you'll encounter choices and dead ends. Note if
 * you flip a binary tree Maze upside down and treat passages as walls and vice
 * versa, the result is basically another binary tree.
 */
public class BinaryTree {

}
