package com.laamella.amazingmazes;

import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;
import static org.junit.Assert.*;


import org.grlea.log.SimpleLogger;
import org.junit.Before;
import org.junit.Test;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.generators.daedalus.*;
import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgram;
import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgramRunnerMazeGenerator;
import com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve.HilbertCurveProgram;
import com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve.PeanoCurveProgram;
import com.laamella.amazingmazes.generators.original.RecursiveBacktrackerMazeGeneratorForMatrices;
import com.laamella.amazingmazes.generators.various.*;
import com.laamella.amazingmazes.mazemodel.*;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.implementation.*;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

public class MazeGeneratorTester {
	static final SimpleLogger log = new SimpleLogger(MazeGeneratorTester.class);

	private StateMatrix mazeMatrix;
	private Grid.UtilityWrapper grid;
	private GridMatrixStorage stateStorage;

	private Randomizer.Default randomGenerator;

	@Before
	public void before() {
		mazeMatrix = new StateMatrix(new Size(19, 19));
		stateStorage = new GridMatrixStorage(mazeMatrix);
		grid = new Grid.UtilityWrapper(new GridWithDecoupledState(stateStorage));
		randomGenerator = new Randomizer.Default();

		mazeMatrix.addObserver(new PrettyPrintObserver(mazeMatrix));

	}

	@Test
	public void testBinaryTreeMazeGenerator() {
		final BinaryTreeMazeGenerator mazeProgram = new BinaryTreeMazeGenerator(randomGenerator);
		mazeProgram.generateMaze(grid);
	}

	@Test
	public void testEllerC64MazeGenerator() {
		final EllerMazeGeneratorC64 mazeProgram = new EllerMazeGeneratorC64(0.5);
		mazeProgram.generateMaze(grid);
	}

	@Test
	public void testAldousBroderMazeGenerator() {
		final AldousBroderMazeGenerator mazeGenerator = new AldousBroderMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
		// assertTrue(new RecursiveBacktrackerSolver().solve(new
		// Graph.UtilityWrapper(grid).getEntrance()));
	}

	@Test
	public void testRecursiveDivisionMazeGenerator() {
		final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator(randomGenerator);
		mazeGenerator.addObserver(new PrettyPrintObserver(mazeMatrix));
		mazeMatrix.deleteObservers();
		mazeGenerator.generateMaze(grid);
	}

	@Test
	public void testRecursiveBacktrackerMazeGenerator() {
		final RecursiveBacktrackerMazeGenerator mazeGenerator = new RecursiveBacktrackerMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
	}

	@Test
	public void testGrowingTreeMazeGenerator() {
		final GrowingTreeMazeGenerator mazeGenerator = new GrowingTreeMazeGenerator(randomGenerator);
		mazeGenerator.generateMaze(grid);
	}

	@Test
	public void testMatrixRecursiveBacktrackerMazeGenerator() {
		final RecursiveBacktrackerMazeGeneratorForMatrices mazeGenerator = new RecursiveBacktrackerMazeGeneratorForMatrices(randomGenerator);
		final StateMatrix stateMatrix = new StateMatrix(new Size(20, 10));
		stateMatrix.addObserver(new PrettyPrintObserver(stateMatrix));
		mazeGenerator.generateMaze(stateMatrix);
	}

	@Test
	public void testRysgaardMazeGenerator() {
		final RysgaardMazeGenerator mazeGenerator = new RysgaardMazeGenerator(randomGenerator);
		final StateMatrix stateMatrix = new StateMatrix(new Size(20, 10));
		stateMatrix.addObserver(new PrettyPrintObserver(stateMatrix));
		mazeGenerator.generateMaze(stateMatrix);
	}

	@Test
	public void testPrimMazeGenerator() {
		final PrimMazeGenerator mazeGenerator = new PrimMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
	}

	@Test
	public void testKruskalMazeGenerator() {
		final KruskalMazeGenerator mazeGenerator = new KruskalMazeGenerator(randomGenerator);
		grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
		mazeGenerator.generateMaze(grid);
	}

	@Test
	public void testSideWinderMazeGenerator() {
		final SideWinderMazeGenerator mazeGenerator = new SideWinderMazeGenerator(randomGenerator);
		mazeGenerator.generateMaze(new GridRowGenerator(grid));
	}

	@Test
	public void testEllerRowGeneratorMazeGenerator() {
		final EllerMazeGenerator mazeGenerator = new EllerMazeGenerator(randomGenerator);
		mazeGenerator.generateMaze(new GridRowGenerator(grid));
	}

	@Test
	public void testHilbertCurveGenerator() {
		final GridLogoProgram mazeProgram = new HilbertCurveProgram(3, false);
		final GridLogoProgramRunnerMazeGenerator spaceFillingCurveMazeGenerator = new GridLogoProgramRunnerMazeGenerator(mazeProgram);
		spaceFillingCurveMazeGenerator.generateMaze(grid);
	}

	@Test
	public void testPeanoCurveGenerator() {
		final GridLogoProgram mazeProgram = new PeanoCurveProgram(2, true);
		final GridLogoProgramRunnerMazeGenerator spaceFillingCurveMazeGenerator = new GridLogoProgramRunnerMazeGenerator(mazeProgram);
		spaceFillingCurveMazeGenerator.generateMaze(grid);
	}

	@Test
	public void testWallSetup() {
		assertTrue(grid.getSquare(new Position(1, 1)).getWall(RIGHT) == grid.getSquare(new Position(2, 1)).getWall(LEFT));
	}

	@Test
	public void testMatrixStorage() {
		grid.getSquare(new Position(4, 3)).setState(15, true);
		assertTrue(mazeMatrix.get(new Position(4 * 2 + 1, 3 * 2 + 1)).hasState(15));
	}
}
