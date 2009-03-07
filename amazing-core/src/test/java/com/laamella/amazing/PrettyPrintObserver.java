/**
 * 
 */
package com.laamella.amazing;

import java.util.Observable;
import java.util.Observer;

import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;

final class PrettyPrintObserver implements Observer {
	private final StateMatrixPrettyPrinter prettyPrinter;
	private final StateMatrix matrix;

	public PrettyPrintObserver(StateMatrix observableMatrix) {
		this.prettyPrinter = new StateMatrixPrettyPrinter();
		this.matrix = observableMatrix;
	}

	public PrettyPrintObserver(StateMatrix observableMatrix, StateMatrixPrettyPrinter prettyPrinter) {
		this.matrix = observableMatrix;
		this.prettyPrinter = prettyPrinter;
	}

	public void update(Observable o, Object arg) {
		MazeGeneratorTester.log.debug(prettyPrinter.getPrintableMaze(matrix));
	}
}