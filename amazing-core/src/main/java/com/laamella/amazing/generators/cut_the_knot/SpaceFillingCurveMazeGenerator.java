package com.laamella.amazing.generators.cut_the_knot;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.GridMazeGenerator;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Turtle;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
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
//		final Turtle turtle = new GridTurtle(grid.getTopLeftSquare(), Direction.RIGHT);
		final Turtle turtle = new GridTurtle(grid.getSquare(new Position(5,5)), Direction.RIGHT);
		program.run(turtle);
		log.exit("generateMaze");
	}

}
