package com.laamella.amazing;

import static com.laamella.amazing.mazemodel.MazeDefinitionState.*;

import java.util.Observable;
import java.util.Observer;

import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.generators.daedalus.PrimMazeGenerator;
import com.laamella.amazing.mazemodel.*;
import com.laamella.amazing.mazemodel.grid.Grid;
import com.laamella.amazing.mazemodel.grid.implementation.GridMatrixStorage;
import com.laamella.amazing.mazemodel.grid.implementation.GridWithDecoupledState;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;
import com.laamella.amazing.operations.MostDistantExitMarkOperation;
import com.laamella.amazing.operations.VertexDistanceMarkingOperation;

public class OperationsTester {
	private static final SimpleLogger log = new SimpleLogger(OperationsTester.class);

	private StateMatrix mazeMatrix;
	private Grid.UtilityWrapper grid;
	private GridMatrixStorage stateStorage;

	private Randomizer.Default randomGenerator;

	private PrimMazeGenerator mazeGenerator;

	private final StateMatrixPrettyPrinter defaultStateMatrixPrettyPrinter = new StateMatrixPrettyPrinter();

	@Before
	public void before() {
		mazeMatrix = new StateMatrix(new Size(149, 43));
		stateStorage = new GridMatrixStorage(mazeMatrix);
		grid = new Grid.UtilityWrapper(new GridWithDecoupledState(stateStorage));
		randomGenerator = new Randomizer.Default();
		mazeGenerator = new PrimMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
		mazeMatrix.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				log.debug(mazeMatrix.toString());
			}
		});
	}

	@Test
	public void testDistanceMarkingOperation() {
		new VertexDistanceMarkingOperation().mark(grid.getSquare(new Position(5, 5)));

		final StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
		stateMatrixPrettyPrinter.map(VertexDistanceMarkingOperation.DISTANCE);
		stateMatrixPrettyPrinter.map(PASSAGE, ' ');
		log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeMatrix));
	}

	@Test
	public void testMostDistanceExitMarkingOperation() {
		new MostDistantExitMarkOperation().findMostDistantExit(grid);

		final StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
		stateMatrixPrettyPrinter.map(VertexDistanceMarkingOperation.DISTANCE);
		stateMatrixPrettyPrinter.map(PASSAGE, ' ');
		log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeMatrix));
		log.debug(defaultStateMatrixPrettyPrinter.getPrintableMaze(mazeMatrix));
	}
}
