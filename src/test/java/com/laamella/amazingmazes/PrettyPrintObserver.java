/**
 * 
 */
package com.laamella.amazingmazes;

import java.util.Observable;
import java.util.Observer;

import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

final class PrettyPrintObserver implements Observer {
	private final StateMatrixPrettyPrinter prettyPrinter;
	private final StateMatrix matrix;

	public PrettyPrintObserver(final StateMatrix observableMatrix) {
		this.prettyPrinter = new StateMatrixPrettyPrinter();
		this.matrix = observableMatrix;
	}

	public PrettyPrintObserver(final StateMatrix observableMatrix, final StateMatrixPrettyPrinter prettyPrinter) {
		this.matrix = observableMatrix;
		this.prettyPrinter = prettyPrinter;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		MazeGeneratorTester.log.debug(prettyPrinter.getPrintableMaze(matrix));
	}
}