package com.laamella.amazingmazes;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.generators.daedalus.*;
import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgram;
import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgramRunnerMazeGenerator;
import com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve.HilbertCurveProgram;
import com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve.PeanoCurveProgram;
import com.laamella.amazingmazes.generators.original.RecursiveBacktrackerMazeGeneratorForMatrices;
import com.laamella.amazingmazes.generators.various.EllerMazeGeneratorC64;
import com.laamella.amazingmazes.generators.various.RecursiveDivisionMazeGenerator;
import com.laamella.amazingmazes.generators.various.RysgaardMazeGenerator;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridMatrixStorage;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridRowGenerator;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridWithDecoupledState;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.mazemodel.grid.Direction.LEFT;
import static com.laamella.amazingmazes.mazemodel.grid.Direction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeGeneratorTester {
    static Logger log = LoggerFactory.getLogger(MazeGeneratorTester.class);

    private StateMatrix mazeMatrix;
    private Grid grid;
    private Randomizer.Default randomGenerator;

    @BeforeEach
     void before() {
        mazeMatrix = new StateMatrix(new Size(19, 19));
        grid = new GridWithDecoupledState(new GridMatrixStorage(mazeMatrix));
        randomGenerator = new Randomizer.Default();

        mazeMatrix.addObserver(new PrettyPrintObserver(mazeMatrix));
    }

    @Test
     void testBinaryTreeMazeGenerator() {
        BinaryTreeMazeGenerator mazeProgram = new BinaryTreeMazeGenerator(randomGenerator);
        mazeProgram.generateMaze(grid);
    }

    @Test
     void testEllerC64MazeGenerator() {
        EllerMazeGeneratorC64 mazeProgram = new EllerMazeGeneratorC64(0.5);
        mazeProgram.generateMaze(grid);
    }

    @Test
     void testAldousBroderMazeGenerator() {
        AldousBroderMazeGenerator mazeGenerator = new AldousBroderMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().setState(ENTRANCE, true);
        mazeGenerator.generateMaze(grid);
        // assertTrue(new RecursiveBacktrackerSolver().solve(new
        // Graph.UtilityWrapper(grid).getEntrance()));
    }

    @Test
     void testRecursiveDivisionMazeGenerator() {
        RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator(randomGenerator);
        mazeGenerator.addObserver(new PrettyPrintObserver(mazeMatrix));
        mazeMatrix.deleteObservers();
        mazeGenerator.generateMaze(grid);
    }

    @Test
     void testRecursiveBacktrackerMazeGenerator() {
        RecursiveBacktrackerMazeGenerator mazeGenerator = new RecursiveBacktrackerMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().setState(ENTRANCE, true);
        mazeGenerator.generateMaze(grid);
    }

    @Test
     void testGrowingTreeMazeGenerator() {
        GrowingTreeMazeGenerator mazeGenerator = new GrowingTreeMazeGenerator(randomGenerator);
        mazeGenerator.generateMaze(grid);
    }

    @Test
     void testMatrixRecursiveBacktrackerMazeGenerator() {
        RecursiveBacktrackerMazeGeneratorForMatrices mazeGenerator = new RecursiveBacktrackerMazeGeneratorForMatrices(
                randomGenerator);
        StateMatrix stateMatrix = new StateMatrix(new Size(20, 10));
        stateMatrix.addObserver(new PrettyPrintObserver(stateMatrix));
        mazeGenerator.generateMaze(stateMatrix);
    }

    @Test
     void testRysgaardMazeGenerator() {
        RysgaardMazeGenerator mazeGenerator = new RysgaardMazeGenerator(randomGenerator);
        StateMatrix stateMatrix = new StateMatrix(new Size(20, 10));
        stateMatrix.addObserver(new PrettyPrintObserver(stateMatrix));
        mazeGenerator.generateMaze(stateMatrix);
    }

    @Test
     void testPrimMazeGenerator() {
        PrimMazeGenerator mazeGenerator = new PrimMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().setState(ENTRANCE, true);
        mazeGenerator.generateMaze(grid);
    }

    @Test
     void testKruskalMazeGenerator() {
        KruskalMazeGenerator mazeGenerator = new KruskalMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().setState(ENTRANCE, true);
        mazeGenerator.generateMaze(grid);
    }

    @Test
     void testSideWinderMazeGenerator() {
        SideWinderMazeGenerator mazeGenerator = new SideWinderMazeGenerator(randomGenerator);
        mazeGenerator.generateMaze(new GridRowGenerator(grid));
    }

    @Test
     void testEllerRowGeneratorMazeGenerator() {
        EllerMazeGenerator mazeGenerator = new EllerMazeGenerator(randomGenerator);
        mazeGenerator.generateMaze(new GridRowGenerator(grid));
    }

    @Test
     void testHilbertCurveGenerator() {
        GridLogoProgram mazeProgram = new HilbertCurveProgram(3, false);
        GridLogoProgramRunnerMazeGenerator spaceFillingCurveMazeGenerator = new GridLogoProgramRunnerMazeGenerator(
                mazeProgram);
        spaceFillingCurveMazeGenerator.generateMaze(grid);
    }

    @Test
     void testPeanoCurveGenerator() {
        GridLogoProgram mazeProgram = new PeanoCurveProgram(2, true);
        GridLogoProgramRunnerMazeGenerator spaceFillingCurveMazeGenerator = new GridLogoProgramRunnerMazeGenerator(
                mazeProgram);
        spaceFillingCurveMazeGenerator.generateMaze(grid);
    }

    @Test
     void testWallSetup() {
        assertSame(grid.getSquare(new Position(1, 1)).getWall(RIGHT), grid.getSquare(new Position(2, 1))
                .getWall(LEFT));
    }

    @Test
     void testMatrixStorage() {
        MazeState s = MazeState.singletonInstance();
        grid.getSquare(new Position(4, 3)).setState(s, true);
        assertTrue(mazeMatrix.get(new Position(4 * 2 + 1, 3 * 2 + 1)).hasState(s));
    }
}
