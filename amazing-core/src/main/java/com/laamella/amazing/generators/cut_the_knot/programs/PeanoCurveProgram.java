package com.laamella.amazing.generators.cut_the_knot.programs;

import com.laamella.amazing.generators.cut_the_knot.LogoProgram;
import com.laamella.amazing.mazemodel.Turtle;

/**
 * <p>
 * http://www.cut-the-knot.org/Curriculum/Geometry/PeanoComplete.shtml
 * <p>
 * http://www.cut-the-knot.org/do_you_know/hilbert.shtml
 * <p>
 * http://www.geom.uiuc.edu/docs/reference/CRC-formulas/node36.html#
 * SECTION01840000000000000000
 * <p>
 * http://www.nio.ntnu.no/archive/2000_2001/2/b1.c
 */
// TODO could be implemented as a kind of LOGO program
// TODO seems to be a ridiculously complex algorithm, redesign it.
public class PeanoCurveProgram implements LogoProgram {
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
	 * @param mirror
	 *            specifies the orientation of the maze: false: left-turning,
	 *            true: right-turning
	 */
	public void maze(final Turtle turtle, final int degree, final boolean mirror) {
		if (degree == 1) {
			turtle.forward();
			left(turtle, mirror);
			turtle.forward();
			left(turtle, mirror);
			turtle.forward();
		} else {
			left(turtle, mirror);
			maze(turtle, degree - 1, !mirror);
			left(turtle, mirror);
			turtle.forward();
			maze(turtle, degree - 1, mirror);
			right(turtle, mirror);
			turtle.forward();
			right(turtle, mirror);
			maze(turtle, degree - 1, mirror);
			turtle.forward();
			left(turtle, mirror);
			maze(turtle, degree - 1, !mirror);
			left(turtle, mirror);
		}
	}
	
	public void run(final Turtle turtle){
		maze(turtle, degree, mirror);
	}
}
