package com.laamella.amazingmazes.generators.daedalus;

import com.laamella.amazingmazes.generators.GraphMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.graph.Edge;
import com.laamella.amazingmazes.mazemodel.graph.Graph;
import com.laamella.amazingmazes.mazemodel.graph.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.PASSAGE;

/**
 * This requires storage proportional to the size of the Maze. During creation,
 * each cell is one of three types:
 * <ol>
 * <li>"In": The cell is part of the Maze and has been carved into already,
 * <li>"Frontier": The cell is not part of the Maze and has not been carved into
 * yet, but is next to a cell that's already "in", and
 * <li>"Out": The cell is not part of the Maze yet, and none of its neighbors
 * are "in" either.
 * </ol>
 * <p>
 * Start by picking a cell, making it "in", and setting all its neighbors to
 * "frontier". Proceed by picking a "frontier" cell at random, and carving into
 * it from one of its neighbor cells that are "in". Change that "frontier" cell
 * to "in", and update any of its neighbors that are "out" to "frontier".
 * <p>
 * The Maze is done when there are no more "frontier" cells left (which means
 * there are no more "out" cells left either, so they're all "in"). This
 * algorithm results in Mazes with a very low "river" factor, with many short
 * dead ends, and the solution is usually pretty direct as well. It also runs
 * very fast when implemented right, with only Eller's algorithm being faster.
 * <p>
 * <a href="http://www.astrolog.org/labyrnth/algrithm.htm">Source of the
 * description</a>
 */
// TODO there's a variant that puts the edges on the frontier
public class PrimMazeGenerator implements GraphMazeGenerator {
    private static final Logger log = LoggerFactory.getLogger(PrimMazeGenerator.class);

    private final Randomizer randomizer;

    public PrimMazeGenerator(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    @Override
    public void generateMaze(Graph graph) {
        log.debug("generateMaze");

        Set<Vertex> in = new HashSet<>();
        Set<Vertex> frontier = new HashSet<>();
        Set<Vertex> out = new HashSet<>(graph.getVertices());

        Vertex startVertex = randomizer.pickOne(out);
        in.add(startVertex);
        out.remove(startVertex);

        makeFrontier(startVertex, frontier, out);

        while (frontier.size() > 0) {
            log.debug("in: " + in.size() + ", frontier: " + frontier.size() + ", out: " + out.size());
            Vertex currentFrontierVertex = randomizer.pickOne(frontier);
            for (Edge edge : currentFrontierVertex.getEdges()) {
                Vertex possibleInVertex = edge.travel(currentFrontierVertex);
                if (in.contains(possibleInVertex)) {
                    edge.setState(PASSAGE, true);
                    in.add(currentFrontierVertex);
                    frontier.remove(currentFrontierVertex);
                    makeFrontier(currentFrontierVertex, frontier, out);
                    break;
                }
            }
        }
    }

    private void makeFrontier(Vertex vertex, Set<Vertex> frontier, Set<Vertex> out) {
        for (Edge edge : vertex.getEdges()) {
            Vertex possibleFrontierVertex = edge.travel(vertex);
            if (out.contains(possibleFrontierVertex)) {
                out.remove(possibleFrontierVertex);
                frontier.add(possibleFrontierVertex);
            }
        }
    }
}
