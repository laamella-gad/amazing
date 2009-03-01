package com.laamella.amazing.generators.spacefillingcurve;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.GridMazeGenerator;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.Turtle;
import com.laamella.amazing.mazemodel.orthogonal.*;
import com.laamella.amazing.mazemodel.orthogonal.implementation.GridTurtle;

/**
 * 
 */
public class SpaceFillingCurveMazeGenerator implements GridMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(SpaceFillingCurveMazeGenerator.class);

	private final LogoProgram program;

	public SpaceFillingCurveMazeGenerator(final LogoProgram program) {
		log.entry("SpaceFillingCurveMazeGenerator");
		this.program = program;
		log.exit("SpaceFillingCurveMazeGenerator");
	}

	@Override
	public void generateMaze(final Grid plainGrid) {
		log.entry("generateMaze");
		final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
		final Square startSquare = grid.getTopLeftSquare();
		startSquare.setState(MazeDefinitionState.ENTRANCE, true);
		final Turtle turtle = new GridTurtle(startSquare, Direction.RIGHT);
		program.run(turtle);
		log.exit("generateMaze");
	}

}
