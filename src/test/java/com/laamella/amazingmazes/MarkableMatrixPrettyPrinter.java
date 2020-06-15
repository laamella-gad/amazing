package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.Marker;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Markable.ObservableMarkable;
import com.laamella.amazingmazes.mazemodel.matrix.Matrix.Visitor;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.MarkableMatrix;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;
import static com.laamella.amazingmazes.solvers.Solver.SOLUTION;

class MarkableMatrixPrettyPrinter {
    private final List<Entry<Marker, Character>> mappings = new ArrayList<>();
    private final char defaultCharacter;

    MarkableMatrixPrettyPrinter() {
        map(ENTRANCE, '>');
        map(EXIT, 'E');
        map(SOLUTION, '.');
        map(PASSAGE, ' ');
        defaultCharacter = '#';
    }

    MarkableMatrixPrettyPrinter(char defaultCharacter) {
        this.defaultCharacter = defaultCharacter;
    }

    void map(Marker marker, Character character) {
        mappings.add(new AbstractMap.SimpleEntry<>(marker, character));
    }

    void map(Marker marker) {
        map(marker, null);
    }

    String getPrintableMaze(MarkableMatrix markableMatrix) {
        StringBuffer maze = new StringBuffer("\n");
        markableMatrix.visitAllSquares(new Visitor<>() {
            public void endRow() {
                maze.append("-\n");
            }

            public void visit(Position position, ObservableMarkable markable) {
                for (Map.Entry<Marker, Character> mapping : mappings) {
                    if (markable.isMarked(mapping.getKey())) {
                        if (mapping.getValue() == null) {
                            maze.append(markable.getNumberMarker(mapping.getKey()) % 10);
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
