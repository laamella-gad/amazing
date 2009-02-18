package com.laamella.amazing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.AldousBroderMazeGenerator;
import com.laamella.amazing.generators.BinaryTreeMazeGenerator;
import com.laamella.amazing.generators.EllerMazeGenerator;
import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.generators.PeanoMazeGenerator;
import com.laamella.amazing.generators.RandomGenerator;
import com.laamella.amazing.mazemodel.Matrix;
import com.laamella.amazing.mazemodel.MazeMatrix;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.StateMatrix;
import com.laamella.amazing.mazemodel.MazeMatrix.State;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.GridMatrixStorageFactory;
import com.laamella.amazing.mazemodel.orthogonal.GridWithDecoupledStorage;

public class MazeGeneratorTester {
	private static final SimpleLogger log = new SimpleLogger(MazeGeneratorTester.class);

	private Matrix.UtilityWrapper<State> matrix;
	private Matrix.UtilityWrapper<Integer> stateMatrix;
	private Grid.UtilityWrapper grid;
	private GridMatrixStorageFactory storageFactory;
	private RandomGenerator.Default randomGenerator;

	@Before
	public void before() {
		matrix = new Matrix.UtilityWrapper<State>(new MazeMatrix(new Size(15, 15)));
		stateMatrix = new Matrix.UtilityWrapper<Integer>(new StateMatrix(new Size(15, 15)));
		storageFactory = new GridMatrixStorageFactory(matrix, stateMatrix);
		grid = new Grid.UtilityWrapper(new GridWithDecoupledStorage(storageFactory));
		randomGenerator = new RandomGenerator.Default();

		matrix.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				log.debug(matrix.toString());
			}
		});
	}

	@Test
	public void testBinaryTreeMazeGenerator() {
		final MazeGenerator mazeProgram = new BinaryTreeMazeGenerator(grid, randomGenerator);
		mazeProgram.generateMaze();
		System.out.println(matrix);
	}

	@Test
	public void testEllerMazeGenerator() {
		final MazeGenerator mazeProgram = new EllerMazeGenerator(grid, 0.5);
		mazeProgram.generateMaze();
		log.debug(matrix.toString());
	}

	@Test
	public void testAldousBroderMazeGenerator() {
		final MazeGenerator mazeGenerator = new AldousBroderMazeGenerator(grid, randomGenerator);
		mazeGenerator.generateMaze();
		log.debug(matrix.toString());
	}

	@Test
	public void testPeanoMazeGenerator() {
		final MazeGenerator mazeProgram = new PeanoMazeGenerator(grid, 1);
		mazeProgram.generateMaze();
		log.debug(matrix.toString());
	}

	@Test
	public void testWallSetup() {
		assertTrue(grid.getSquare(new Position(1, 1)).getWall(Direction.RIGHT) == grid.getSquare(new Position(2, 1)).getWall(Direction.LEFT));
	}

	@Test
	public void testMatrixStorage() {
		grid.getSquare(new Position(4, 3)).setState(15);
		assertEquals(15, stateMatrix.get(new Position(4 * 2 + 1, 3 * 2 + 1)));
	}
}
