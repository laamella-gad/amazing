package com.laamella.amazing;

import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.generators.perfect.*;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
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
		final MazeGenerator mazeProgram = new BinaryTreeMazeGenerator(grid, randomGenerator);
		mazeProgram.generateMaze();
		System.out.println(mazeMatrix);
	}

	@Test
	public void testEllerMazeGenerator() {
		final MazeGenerator mazeProgram = new EllerMazeGenerator(grid, 0.5);
		mazeProgram.generateMaze();
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testAldousBroderMazeGenerator() {
		final MazeGenerator mazeGenerator = new AldousBroderMazeGenerator(grid, randomGenerator);
		mazeGenerator.generateMaze();
		assertTrue(new RecursiveBacktrackerSolver().solve(new Graph.UtilityWrapper(grid).getEntrance()));
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testRecursiveDivisionMazeGenerator() {
		final MazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator(grid, randomGenerator);
		mazeGenerator.generateMaze();
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testRecursiveBacktrackerMazeGenerator() {
		final MazeGenerator mazeGenerator = new RecursiveBacktrackerMazeGenerator(grid.getTopLeftSquare(), randomGenerator);
		mazeGenerator.generateMaze();
		log.debug(mazeMatrix.toString());
	}
	@Test
	public void testPrimMazeGenerator() {
		final MazeGenerator mazeGenerator = new PrimMazeGenerator(grid.getTopLeftSquare(), randomGenerator);
		mazeGenerator.generateMaze();
		log.debug(mazeMatrix.toString());
	}

	@Test
	public void testPeanoMazeGenerator() {
		final MazeGenerator mazeProgram = new PeanoMazeGenerator(grid, 1);
		mazeProgram.generateMaze();
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
