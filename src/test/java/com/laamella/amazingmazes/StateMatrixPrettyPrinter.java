package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Stateful.ObservableObjectSetState;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix.Visitor;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.solvers.Solver.SOLUTION;

class StateMatrixPrettyPrinter {
    private final List<Entry<MazeState, Character>> mappings = new ArrayList<>();
    private final char defaultCharacter;

    StateMatrixPrettyPrinter() {
        map(ENTRANCE, '>');
        map(EXIT, 'E');
        map(SOLUTION, '.');
        map(PASSAGE, ' ');
        defaultCharacter = '#';
    }

    StateMatrixPrettyPrinter(char defaultCharacter) {
        this.defaultCharacter = defaultCharacter;
    }

    void map(final MazeState state, final Character character) {
        mappings.add(new AbstractMap.SimpleEntry<>(state, character));
    }

    void map(final MazeState state) {
        map(state, null);
    }

    String getPrintableMaze(final StateMatrix stateMatrix) {
        final StringBuffer maze = new StringBuffer("\n");
        stateMatrix.visitAllSquares(new Visitor<>() {
            public void endRow() {
                maze.append("-\n");
            }

            public void visit(final Position position, final ObservableObjectSetState states) {
                for (final Map.Entry<MazeState, Character> mapping : mappings) {
                    if (states.hasState(mapping.getKey())) {
                        if (mapping.getValue() == null) {
                            maze.append(states.getState(mapping.getKey()) % 10);
                        } else {
                            maze.append(mapping.getValue());
                        }
                        return;

                    }
                }
                maze.append(defaultCharacter);
            }

            public void startRow() {
                maze.append("-");
            }
        });
        return maze.toString();
    }

}
