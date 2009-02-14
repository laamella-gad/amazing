package com.laamella.daedalousy;

import org.junit.Test;

import com.laamella.daedalousy.generators.Eller;
import com.laamella.daedalousy.mazemodel.ArrayMatrix;
import com.laamella.daedalousy.mazemodel.Matrix;
import com.laamella.daedalousy.mazemodel.orthogonal.Grid;
import com.laamella.daedalousy.mazemodel.orthogonal.GridDefault;
import com.laamella.daedalousy.mazemodel.orthogonal.GridToMatrixAdapter;

public class DaedalousyTester {
	@Test
	public void testEllerC64Generator() {
		final Matrix matrix = new ArrayMatrix(9, 9);
		final Matrix stateMatrix = new ArrayMatrix(9, 9);
		final Grid grid = new GridToMatrixAdapter(matrix, stateMatrix);
		final Eller mazeProgram = new Eller(grid);
		mazeProgram.createMaze(0.5);
		System.out.println(matrix);
	}

	@Test
	public void testGridWaller() {
		final Matrix matrix = new ArrayMatrix(9, 9);
		final Matrix stateMatrix = new ArrayMatrix(9, 9);
		final Grid grid = new GridToMatrixAdapter(matrix, stateMatrix);
//		GridManipulator.closeAllWalls(grid);
		System.out.println(matrix);

	}
}
