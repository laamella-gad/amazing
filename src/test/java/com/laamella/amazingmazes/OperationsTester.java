package com.laamella.amazingmazes;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.generators.daedalus.PrimMazeGenerator;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridMatrixStorage;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridWithDecoupledState;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;
import com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker;
import com.laamella.amazingmazes.operations.MostDistantEntranceAndExitFinder;
import com.laamella.amazingmazes.operations.MostDistantExitMarker;
import com.laamella.amazingmazes.operations.VertexDistanceMarker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;
import static com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker.*;
import static com.laamella.amazingmazes.operations.VertexDistanceMarker.*;

public class OperationsTester {
    private static final Logger log = LoggerFactory.getLogger(OperationsTester.class);

    private final StateMatrix mazeStateMatrix = new StateMatrix(new Size(149, 41));
    private final GridMatrixStorage stateStorage = new GridMatrixStorage(mazeStateMatrix);
    private final GridWithDecoupledState grid = new GridWithDecoupledState(stateStorage);

    private final StateMatrixPrettyPrinter defaultStateMatrixPrettyPrinter = new StateMatrixPrettyPrinter();

    @BeforeEach
     void before() {
        Randomizer.Default randomGenerator = new Randomizer.Default();
        PrimMazeGenerator mazeGenerator = new PrimMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().setState(MazeDefinitionState.ENTRANCE, true);
        mazeGenerator.generateMaze(grid);
        mazeStateMatrix.addObserver(new PrettyPrintObserver(mazeStateMatrix));
    }

    @Test
     void testDistanceMarkingOperation() {
        new VertexDistanceMarker().mark(grid.getSquare(new Position(5, 5)));

        StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
        stateMatrixPrettyPrinter.map(DISTANCE);
        stateMatrixPrettyPrinter.map(PASSAGE, ' ');
        log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeStateMatrix));
    }

    @Test
     void testMostDistanceExitMarkingOperation() {
        new MostDistantExitMarker().findMostDistantExit(grid);

        StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
        stateMatrixPrettyPrinter.map(DISTANCE);
        stateMatrixPrettyPrinter.map(PASSAGE, ' ');
        log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeStateMatrix));
        log.debug(defaultStateMatrixPrettyPrinter.getPrintableMaze(mazeStateMatrix));
    }

    @Test
     void testDistanceFromDeadEndMarkerOperation() {
        DistanceFromDeadEndMarker longestPathFinderOperation = new DistanceFromDeadEndMarker();

        StateMatrixPrettyPrinter stateMatrixPrettyPrinter = new StateMatrixPrettyPrinter('#');
        stateMatrixPrettyPrinter.map(DISTANCE_FROM_DEAD_END);
        stateMatrixPrettyPrinter.map(PASSAGE, ' ');

        longestPathFinderOperation.addObserver(new PrettyPrintObserver(mazeStateMatrix, stateMatrixPrettyPrinter));

        longestPathFinderOperation.execute(grid);

        log.debug(stateMatrixPrettyPrinter.getPrintableMaze(mazeStateMatrix));
    }

    @Test
     void testMostDistantEntranceAndExitFinder() {
        grid.clearState(ENTRANCE);
        grid.clearState(EXIT);
        new DistanceFromDeadEndMarker().execute(grid);
        new MostDistantEntranceAndExitFinder().execute(grid);
    }

}
