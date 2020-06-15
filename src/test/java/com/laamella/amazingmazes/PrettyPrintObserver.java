package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;
import com.laamella.amazingmazes.observe.Observable;
import com.laamella.amazingmazes.observe.Observer;

final class PrettyPrintObserver implements Observer {
    private final StateMatrixPrettyPrinter prettyPrinter;
    private final StateMatrix matrix;

    PrettyPrintObserver(StateMatrix observableMatrix) {
        this.prettyPrinter = new StateMatrixPrettyPrinter();
        this.matrix = observableMatrix;
    }

    PrettyPrintObserver(StateMatrix observableMatrix, StateMatrixPrettyPrinter prettyPrinter) {
        this.matrix = observableMatrix;
        this.prettyPrinter = prettyPrinter;
    }

    @Override
    public void update() {
        MazeGeneratorTester.log.debug(prettyPrinter.getPrintableMaze(matrix));
    }
}