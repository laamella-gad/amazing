package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.matrix.implementation.MarkableMatrix;
import com.laamella.amazingmazes.observe.Observer;

final class PrettyPrintObserver implements Observer {
    private final MarkableMatrixPrettyPrinter prettyPrinter;
    private final MarkableMatrix matrix;

    PrettyPrintObserver(MarkableMatrix observableMatrix) {
        this.prettyPrinter = new MarkableMatrixPrettyPrinter();
        this.matrix = observableMatrix;
    }

    PrettyPrintObserver(MarkableMatrix observableMatrix, MarkableMatrixPrettyPrinter prettyPrinter) {
        this.matrix = observableMatrix;
        this.prettyPrinter = prettyPrinter;
    }

    @Override
    public void update() {
        MazeGeneratorTester.log.debug(prettyPrinter.getPrintableMaze(matrix));
    }
}