package com.laamella.amazingmazes.generators.daedalus;

import com.laamella.amazingmazes.generators.GraphMazeGenerator;
import com.laamella.amazingmazes.mazemodel.graph.Graph;

/**
 * This is an improved version of the Aldous-Broder algorithm, in that it
 * produces Mazes exactly like that algorithm, with all possible Mazes generated
 * with equal probability, except that Wilson's algorithm runs much faster. It
 * requires storage up to the size of the Maze.
 * <p>
 * Begin by making a random starting cell part of the Maze. Proceed by picking a
 * random cell not already part of the Maze, and doing a random walk until a
 * cell is found which is already part of the Maze. Once the already created
 * part of the Maze is hit, go back to the random cell that was picked, and
 * carve along the path that was taken, adding those cells to the Maze. More
 * specifically, when retracing the path, at each cell carve along the direction
 * that the random walk most recently took when it left that cell. That avoids
 * adding loops along the retraced path, resulting in a single long passage
 * being appended to the Maze. The Maze is done when all cells have been
 * appended to the Maze.
 * <p>
 * This has similar performance issues as Aldous-Broder, where it may take a
 * long time for the first random path to find the starting cell, however once a
 * few paths are in place, the rest of the Maze gets carved quickly. On average
 * this runs five times faster than Aldous-Broder, and takes less than twice as
 * long as the top algorithms. Note this runs twice as fast when implemented as
 * a wall adder, because the whole boundary wall starts as part of the Maze, so
 * the first walls are connected much quicker.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 * <h2>According to <a
 * href="http://forums.xkcd.com/viewtopic.php?f=12&t=34293">xkcd forum</a></h2>
 * <p>
 * Pick any node, and set it as the "tree so far."
 * <ol>
 * <li>Pick a node not in the "tree so far" uniformly at random to be the
 * current node. Call it the "start node."
 * <li>Pick a neighbor of the current node uniformly at random.
 * <li>Mark the "out edge" of the current node as pointing to the neighbor.
 * <li>Set the neighbor as the current node and go to step 2, unless the
 * neighbor is a part of the "tree so far," in which case, go to step 5.
 * <li>Starting at the "start node," follow the "out edges," adding each in turn
 * to the "tree so far."
 * <li>If the "tree so far" contains n-1 edges, halt, else go back to step 1.
 * </ol>
 * <h2><a href="http://gilkalai.wordpress.com/2008/07/06/from-helly-to-cayley-iv-probability-questions/"
 * >Gil Kalai's blog</a></h2>
 * <p>
 * <ol>
 * <li>Mark a vertex as a root. (at a later stage the root will be a subtree.)
 * <li>Choose an arbitrary vertex not in the root and make a random walk until
 * hitting the root.
 * <li>Delete all cycles created in this walk and add the remaining path to the
 * root.
 * </ol>
 * Returning this process also leads to a uniform random spanning tree.
 */
public class WilsonMazeGenerator implements GraphMazeGenerator {

    @Override
    public void generateMaze(Graph graph) {
        // TODO create Wilson algorithm

    }
}
