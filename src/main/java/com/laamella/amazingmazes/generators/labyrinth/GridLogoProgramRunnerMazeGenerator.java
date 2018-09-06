package com.laamella.amazingmazes.generators.labyrinth;

import com.laamella.amazingmazes.generators.GridMazeGenerator;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridTurtle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class GridLogoProgramRunnerMazeGenerator implements GridMazeGenerator {
    private static Logger log = LoggerFactory.getLogger(GridLogoProgramRunnerMazeGenerator.class);

    private final GridLogoProgram program;

    public GridLogoProgramRunnerMazeGenerator(final GridLogoProgram program) {
        log.debug("GridLogoProgramRunnerMazeGenerator");
        this.program = program;
    }

    @Override
    public void generateMaze(final Grid plainGrid) {
        log.debug("generateMaze");
        final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
        final Square startSquare = program.getStartSquare(grid);
        startSquare.setState(MazeDefinitionState.ENTRANCE, true);
        final Turtle turtle = new GridTurtle(startSquare, Direction.RIGHT);
        program.run(turtle);
    }
}
