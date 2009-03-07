package com.laamella.amazing.generators.labyrinth.spacefillingcurve;

import com.laamella.amazing.generators.labyrinth.GridLogoProgram;
import com.laamella.amazing.mazemodel.Turtle;
import com.laamella.amazing.mazemodel.grid.Square;
import com.laamella.amazing.mazemodel.grid.Grid.UtilityWrapper;

/**
 * <p>
 * http://www.cut-the-knot.org/ctk/Mazes.shtml
 * <p>
 * http://en.wikipedia.org/wiki/Moore_curve
 */
public class MooreCurveProgram implements GridLogoProgram {

	@Override
	public void run(Turtle turtle) {
		// TODO create Moore curve algorithm
	}
	@Override
	public Square getStartSquare(UtilityWrapper grid) {
		return grid.getTopLeftSquare();
	}
}
