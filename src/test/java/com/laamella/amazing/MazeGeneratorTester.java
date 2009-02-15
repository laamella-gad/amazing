package com.laamella.amazing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.laamella.amazing.generators.BinaryTreeMazeGenerator;
import com.laamella.amazing.generators.EllerMazeGenerator;
import com.laamella.amazing.generators.MazeGenerator;
import com.laamella.amazing.mazemodel.ArrayMatrix;
import com.laamella.amazing.mazemodel.Matrix;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.Matrix.MatrixUtilityWrapper;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.GridMatrixStorageFactory;
import com.laamella.amazing.mazemodel.orthogonal.GridWithDecoupledStorage;
import com.laamella.amazing.mazemodel.orthogonal.Grid.GridUtilityWrapper;
import com.laamella.amazing.mazemodel.orthogonal.Square.Direction;

public class MazeGeneratorTester {
	private MatrixUtilityWrapper matrix;
	private MatrixUtilityWrapper stateMatrix;
	private GridUtilityWrapper grid;
	private GridMatrixStorageFactory storageFactory;

	@Before
	public void before() {
		matrix = new Matrix.MatrixUtilityWrapper(new ArrayMatrix(new Size(15, 15)));
		stateMatrix = new Matrix.MatrixUtilityWrapper(new ArrayMatrix(new Size(15, 15)));
		storageFactory = new GridMatrixStorageFactory(matrix, stateMatrix);
		grid = new Grid.GridUtilityWrapper(new GridWithDecoupledStorage(storageFactory));
	}

	@Test
	public void testBinaryTreeAlgorithm() {
		final MazeGenerator mazeProgram = new BinaryTreeMazeGenerator(grid);
		mazeProgram.generateMaze();
		System.out.println(matrix);
	}

	@Test
	public void testEllerGenerator() {
		final MazeGenerator mazeProgram = new EllerMazeGenerator(grid, 0.5);
		mazeProgram.generateMaze();
		System.out.println(matrix);
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
