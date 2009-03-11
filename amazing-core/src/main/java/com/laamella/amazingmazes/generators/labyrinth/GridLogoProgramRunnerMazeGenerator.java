package com.laamella.amazingmazes.generators.labyrinth;

import org.grlea.log.SimpleLogger;

import com.laamella.amazingmazes.generators.GridMazeGenerator;
import com.laamella.amazingmazes.mazemodel.MazeDefinitionState;
import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.*;
import com.laamella.amazingmazes.mazemodel.grid.implementation.GridTurtle;

/**
 * 
 */
public class GridLogoProgramRunnerMazeGenerator implements GridMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(GridLogoProgramRunnerMazeGenerator.class);

	private final GridLogoProgram program;

	public GridLogoProgramRunnerMazeGenerator(final GridLogoProgram program) {
		log.entry("GridLogoProgramRunnerMazeGenerator");
		this.program = program;
		log.exit("GridLogoProgramRunnerMazeGenerator");
	}

	@Override
	public void generateMaze(final Grid plainGrid) {
		log.entry("generateMaze");
		final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
		final Square startSquare = program.getStartSquare(grid);
		startSquare.setState(MazeDefinitionState.ENTRANCE, true);
		final Turtle turtle = new GridTurtle(startSquare, Direction.RIGHT);
		program.run(turtle);
		log.exit("generateMaze");
	}
}
