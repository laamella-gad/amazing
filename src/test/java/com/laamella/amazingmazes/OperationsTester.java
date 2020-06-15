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

public class OperationsTester {
    private static final Logger log = LoggerFactory.getLogger(OperationsTester.class);

    private final MarkableMatrix mazeMarkableMatrix = new MarkableMatrix(new Size(149, 41));
    private final GridMatrixStorage matrixStorage = new GridMatrixStorage(mazeMarkableMatrix);
    private final GridWithDecoupledMarkers grid = new GridWithDecoupledMarkers(matrixStorage);

    private final MarkableMatrixPrettyPrinter defaultMarkableMatrixPrettyPrinter = new MarkableMatrixPrettyPrinter();

    @BeforeEach
     void before() {
        Randomizer.Default randomGenerator = new Randomizer.Default();
        PrimMazeGenerator mazeGenerator = new PrimMazeGenerator(randomGenerator);
        grid.getTopLeftSquare().mark(ENTRANCE);
        mazeGenerator.generateMaze(grid);
        mazeMarkableMatrix.addObserver(new PrettyPrintObserver(mazeMarkableMatrix));
    }

    @Test
     void testDistanceMarkingOperation() {
        new VertexDistanceMarker().mark(grid.getSquare(new Position(5, 5)));

        MarkableMatrixPrettyPrinter markableMatrixPrettyPrinter = new MarkableMatrixPrettyPrinter('#');
        markableMatrixPrettyPrinter.map(DISTANCE);
        markableMatrixPrettyPrinter.map(PASSAGE, ' ');
        log.debug(markableMatrixPrettyPrinter.getPrintableMaze(mazeMarkableMatrix));
    }

    @Test
     void testMostDistanceExitMarkingOperation() {
        new MostDistantExitMarker().findMostDistantExit(grid);

        MarkableMatrixPrettyPrinter markableMatrixPrettyPrinter = new MarkableMatrixPrettyPrinter('#');
        markableMatrixPrettyPrinter.map(DISTANCE);
        markableMatrixPrettyPrinter.map(PASSAGE, ' ');
        log.debug(markableMatrixPrettyPrinter.getPrintableMaze(mazeMarkableMatrix));
        log.debug(defaultMarkableMatrixPrettyPrinter.getPrintableMaze(mazeMarkableMatrix));
    }

    @Test
     void testDistanceFromDeadEndMarkerOperation() {
        DistanceFromDeadEndMarker longestPathFinderOperation = new DistanceFromDeadEndMarker();

        MarkableMatrixPrettyPrinter markableMatrixPrettyPrinter = new MarkableMatrixPrettyPrinter('#');
        markableMatrixPrettyPrinter.map(DISTANCE_FROM_DEAD_END);
        markableMatrixPrettyPrinter.map(PASSAGE, ' ');

        longestPathFinderOperation.addObserver(new PrettyPrintObserver(mazeMarkableMatrix, markableMatrixPrettyPrinter));

        longestPathFinderOperation.execute(grid);

        log.debug(markableMatrixPrettyPrinter.getPrintableMaze(mazeMarkableMatrix));
    }

    @Test
     void testMostDistantEntranceAndExitFinder() {
        grid.clearMarker(ENTRANCE);
        grid.clearMarker(EXIT);
        new DistanceFromDeadEndMarker().execute(grid);
        new MostDistantEntranceAndExitFinder().execute(grid);
    }

}
