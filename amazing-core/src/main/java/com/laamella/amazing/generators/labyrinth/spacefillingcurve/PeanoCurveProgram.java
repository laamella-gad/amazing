package com.laamella.amazing.generators.labyrinth.spacefillingcurve;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.labyrinth.GridLogoProgram;
import com.laamella.amazing.mazemodel.Turtle;
import com.laamella.amazing.mazemodel.grid.Square;
import com.laamella.amazing.mazemodel.grid.Grid.UtilityWrapper;

/**
 * <p>
 * http://www.cut-the-knot.org/Curriculum/Geometry/PeanoComplete.shtml
 * <p>
 * http://www.geom.uiuc.edu/docs/reference/CRC-formulas/node36.html#
 * SECTION01840000000000000000
 * <p>
 * http://www.cut-the-knot.org/ctk/Mazes.shtml
 * <p>
 * http://people.csail.mit.edu/jaffer/Geometry/PSFC
 * <p>
 * <a href="http://tog.acm.org/GraphicsGems/gemsii/Hilbert.c">Graphics Gems 2
 * code</a> that was used here.
 */
public class PeanoCurveProgram implements GridLogoProgram {
	private static final SimpleLogger log = new SimpleLogger(PeanoCurveProgram.class);

	private final int degree;
	private final boolean mirror;

	public PeanoCurveProgram(final int degree, final boolean mirror) {
		this.degree = degree;
		this.mirror = mirror;
	}

	private void right(Turtle turtle, boolean mirror) {
		if (mirror) {
			turtle.left();
		} else {
			turtle.right();
		}
	}

	private void left(Turtle turtle, boolean mirror) {
		if (mirror) {
			turtle.right();
		} else {
			turtle.left();
		}
	}

	/**
	 * @param rightTurning
	 *            specifies the orientation of the maze: false: left-turning,
	 *            true: right-turning
	 */
	public void drawCurve(final Turtle turtle, final int degree, final boolean rightTurning) {
		if (degree == 0) {
			return;
		}
		log.debug("draw(" + turtle + ", " + degree + ", " + rightTurning + ")");
		drawCurve(turtle, degree - 1, rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, !rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, rightTurning);
		left(turtle, rightTurning);
		turtle.walk();
		left(turtle, rightTurning);
		drawCurve(turtle, degree - 1, !rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, !rightTurning);
		right(turtle, rightTurning);
		turtle.walk();
		right(turtle, rightTurning);
		drawCurve(turtle, degree - 1, rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, !rightTurning);
		turtle.walk();
		drawCurve(turtle, degree - 1, rightTurning);
	}

	public void run(final Turtle turtle) {
		drawCurve(turtle, degree, mirror);
	}

	@Override
	public Square getStartSquare(UtilityWrapper grid) {
		return grid.getTopLeftSquare();
	}
}
