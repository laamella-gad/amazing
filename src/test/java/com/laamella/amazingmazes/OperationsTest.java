package com.laamella.amazingmazes;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.generators.daedalus.PrimMazeGenerator;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridMatrixStorage;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridWithDecoupledMarkers;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.MarkableMatrix;
import com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker;
import com.laamella.amazingmazes.operations.MostDistantEntranceAndExitFinder;
import com.laamella.amazingmazes.operations.MostDistantExitMarker;
import com.laamella.amazingmazes.operations.VertexDistanceMarker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;
import static com.laamella.amazingmazes.operations.DistanceFromDeadEndMarker.*;
import static com.laamella.amazingmazes.operations.VertexDistanceMarker.*;

public class OperationsTest {
    private static final Logger log = LoggerFactory.getLogger(OperationsTest.class);

    private final MarkableMatrix mazeMatrix = new MarkableMatrix(new Size(149, 41));
    private final GridMatrixStorage matrixStorage = new GridMatrixStorage(mazeMatrix);
    private final GridWithDecoupledMarkers grid = new GridWithDecoupledMarkers(matrixStorage);

    private final MarkableMatrixPrettyPrinter defaultPrinter = new MarkableMatrixPrettyPrinter();

    @BeforeEach
     void before() {
        Randomizer randomGenerator = new Randomizer.Default();
        PrimMazeGenerator generator = new PrimMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().mark(ENTRANCE);
        generator.generateMaze(grid);
        mazeMatrix.addObserver(new PrettyPrintObserver(mazeMatrix));
    }

    @Test
     void testDistanceMarkingOperation() {
        new VertexDistanceMarker().mark(grid.getSquare(new Position(5, 5)));

        MarkableMatrixPrettyPrinter printer = new MarkableMatrixPrettyPrinter('#');
        printer.map(DISTANCE);
        printer.map(PASSAGE, ' ');
        log.debug(printer.getPrintableMaze(mazeMatrix));
    }

    @Test
     void testMostDistanceExitMarkingOperation() {
        new MostDistantExitMarker().findMostDistantExit(grid);

        MarkableMatrixPrettyPrinter printer = new MarkableMatrixPrettyPrinter('#');
        printer.map(DISTANCE);
        printer.map(PASSAGE, ' ');
        log.debug(printer.getPrintableMaze(mazeMatrix));
        log.debug(defaultPrinter.getPrintableMaze(mazeMatrix));
    }

    @Test
     void testDistanceFromDeadEndMarkerOperation() {
        DistanceFromDeadEndMarker longestPathFinder = new DistanceFromDeadEndMarker();

        MarkableMatrixPrettyPrinter printer = new MarkableMatrixPrettyPrinter('#');
        printer.map(DISTANCE_FROM_DEAD_END);
        printer.map(PASSAGE, ' ');

        longestPathFinder.addObserver(new PrettyPrintObserver(mazeMatrix, printer));

        longestPathFinder.execute(grid);

        log.debug(printer.getPrintableMaze(mazeMatrix));
    }

    @Test
     void testMostDistantEntranceAndExitFinder() {
        grid.clearMarker(ENTRANCE);
        grid.clearMarker(EXIT);
        new DistanceFromDeadEndMarker().execute(grid);
        new MostDistantEntranceAndExitFinder().execute(grid);
    }

}
