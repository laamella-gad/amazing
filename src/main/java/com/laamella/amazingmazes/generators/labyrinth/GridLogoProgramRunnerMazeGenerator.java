package com.laamella.amazingmazes.generators.labyrinth;

import com.laamella.amazingmazes.generators.GridMazeGenerator;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker;
import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridTurtle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;

/**
 *
 */
public class GridLogoProgramRunnerMazeGenerator implements GridMazeGenerator {
    private static final Logger log = LoggerFactory.getLogger(GridLogoProgramRunnerMazeGenerator.class);

    private final GridLogoProgram program;

    public GridLogoProgramRunnerMazeGenerator(GridLogoProgram program) {
        log.debug("GridLogoProgramRunnerMazeGenerator");
        this.program = program;
    }

    @Override
    public void generateMaze(Grid grid) {
        log.debug("generateMaze");
        Square startSquare = program.getStartSquare(grid);
        startSquare.mark(ENTRANCE);
        Turtle turtle = new GridTurtle(startSquare, Direction.RIGHT);
        program.run(turtle);
    }
}
