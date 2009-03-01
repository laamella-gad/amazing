package com.laamella.amazing.generators.cut_the_knot;

import com.laamella.amazing.mazemodel.Turtle;

/**
 * A tiny graphical language based on Logo. Uses a turtle as in logo, and knows
 * only a few commands, but enough to draw paths through mazes.
 */
public interface LogoProgram {
	void run(Turtle turtle);
}