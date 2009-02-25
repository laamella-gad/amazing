package com.laamella.amazing;

import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;

import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.generators.cut_the_knot.PeanoMazeGenerator;
import com.laamella.amazing.generators.daedalus.*;
import com.laamella.amazing.generators.various.EllerMazeGenerator;
import com.laamella.amazing.generators.various.RecursiveDivisionMazeGenerator;
import com.laamella.amazing.mazemodel.*;
import com.laamella.amazing.mazemodel.graph.Graph;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.implementation.GridMatrixStorage;
import com.laamella.amazing.mazemodel.orthogonal.implementation.GridWithDecoupledState;
import com.laamella.amazing.solvers.RecursiveBacktrackerSolver;

public class MazeGeneratorTester {
	private static final SimpleLogger log = new SimpleLogger(MazeGeneratorTester.class);

	private StateMatrix mazeMatrix;
	private Grid.UtilityWrapper grid;
	private GridMatrixStorage stateStorage;

	private Randomizer.Default randomGenerator;

	private final StateMatrixPrettyPrinter prettyPrinter = new StateMatrixPrettyPrinter();

	@Before
	public void before() {
		mazeMatrix = new StateMatrix(new Size(15, 15));
		stateStorage = new GridMatrixStorage(mazeMatrix);
		grid = new Grid.UtilityWrapper(new GridWithDecoupledState(stateStorage));
		randomGenerator = new Randomizer.Default();

		mazeMatrix.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				log.debug(mazeMatrix.toString());
			}
		});

	}

	@Test
	public void testBinaryTreeMazeGenerator() {
		final BinaryTreeMazeGenerator mazeProgram = new BinaryTreeMazeGenerator(randomGenerator);
		mazeProgram.generateMaze(grid);
		System.out.println(mazeMatrix);
	}

	@Test
	public void testEllerMazeGenerator() {
		final EllerMazeGenerator mazeProgram = new EllerMazeGenerator(0.5);
		mazeProgram.generateMaze(grid);
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testAldousBroderMazeGenerator() {
		final AldousBroderMazeGenerator mazeGenerator = new AldousBroderMazeGenerator(randomGenerator);
		mazeGenerator.generateMaze(grid);
		assertTrue(new RecursiveBacktrackerSolver().solve(new Graph.UtilityWrapper(grid).getEntrance()));
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testRecursiveDivisionMazeGenerator() {
		final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator(randomGenerator);
		mazeGenerator.generateMaze(grid);
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testRecursiveBacktrackerMazeGenerator() {
		final RecursiveBacktrackerMazeGenerator mazeGenerator = new RecursiveBacktrackerMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testPrimMazeGenerator() {
		final PrimMazeGenerator mazeGenerator = new PrimMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testKruskalMazeGenerator() {
		final KruskalMazeGenerator mazeGenerator = new KruskalMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
		log.debug(prettyPrinter.getPrintableMaze(mazeMatrix));
	}

	@Test
	public void testPeanoMazeGenerator() {
		final PeanoMazeGenerator mazeProgram = new PeanoMazeGenerator(1);
		mazeProgram.generateMaze(grid);
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testWallSetup() {
		assertTrue(grid.getSquare(new Position(1, 1)).getWall(Direction.RIGHT) == grid.getSquare(new Position(2, 1)).getWall(Direction.LEFT));
	}

	@Test
	public void testMatrixStorage() {
		grid.getSquare(new Position(4, 3)).setState(15, true);
		assertTrue(mazeMatrix.get(new Position(4 * 2 + 1, 3 * 2 + 1)).hasState(15));
	}
}
