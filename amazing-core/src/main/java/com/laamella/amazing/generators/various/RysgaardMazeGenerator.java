package com.laamella.amazing.generators.various;

import com.laamella.amazing.generators.MatrixMazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;

/**
 * <a href="http://www.glimt.dk/code/labyrinth.htm">A nice algorithm</a>
 */
public class RysgaardMazeGenerator implements MatrixMazeGenerator {

	private final Randomizer randomizer;

	public RysgaardMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(StateMatrix matrix) {
		// TODO Rysgaard algorithm

	}

}
