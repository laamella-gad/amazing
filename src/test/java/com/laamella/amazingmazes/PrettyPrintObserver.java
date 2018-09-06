/**
 *
 */
package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

import java.util.Observable;
import java.util.Observer;

final class PrettyPrintObserver implements Observer {
    private final StateMatrixPrettyPrinter prettyPrinter;
    private final StateMatrix matrix;

    PrettyPrintObserver(final StateMatrix observableMatrix) {
        this.prettyPrinter = new StateMatrixPrettyPrinter();
        this.matrix = observableMatrix;
    }

    PrettyPrintObserver(final StateMatrix observableMatrix, final StateMatrixPrettyPrinter prettyPrinter) {
        this.matrix = observableMatrix;
        this.prettyPrinter = prettyPrinter;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        MazeGeneratorTester.log.debug(prettyPrinter.getPrintableMaze(matrix));
    }
}