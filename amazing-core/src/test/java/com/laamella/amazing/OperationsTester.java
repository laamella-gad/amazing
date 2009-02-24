package com.laamella.amazing;

import static com.laamella.amazing.mazemodel.MazeDefinitionState.*;

import java.util.Observable;
import java.util.Observer;

import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.generators.perfect.PrimMazeGenerator;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.implementation.GridMatrixStorage;
import com.laamella.amazing.mazemodel.orthogonal.implementation.GridWithDecoupledState;
import com.laamella.amazing.operations.VertexDistanceMarkingOperation;

public class OperationsTester {
	private static final SimpleLogger log = new SimpleLogger(OperationsTester.class);

	private StateMatrix mazeMatrix;
	private Grid.UtilityWrapper grid;
	private GridMatrixStorage stateStorage;

	private Randomizer.Default randomGenerator;

	private PrimMazeGenerator mazeGenerator;

	@Before
	public void before() {
		mazeMatrix = new StateMatrix(new Size(15, 15));
		stateStorage = new GridMatrixStorage(mazeMatrix);
		grid = new Grid.UtilityWrapper(new GridWithDecoupledState(stateStorage));
		randomGenerator = new Randomizer.Default();
		mazeGenerator = new PrimMazeGenerator(grid.getTopLeftSquare(), randomGenerator);
		mazeGenerator.generateMaze();
		mazeMatrix.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				log.debug(mazeMatrix.toString());
			}
		});
	}

	@Test
	public void testDistanceMarkingOperation() {
		new VertexDistanceMarkingOperation().process(grid.getSquare(new Position(5, 5)));
		final StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
		stateMatrixPrettyPrinter.map(VertexDistanceMarkingOperation.DISTANCE);
		stateMatrixPrettyPrinter.map(PASSAGE, ' ');
		log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeMatrix));
	}

}
