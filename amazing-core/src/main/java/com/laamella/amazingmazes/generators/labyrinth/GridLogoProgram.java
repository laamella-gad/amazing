package com.laamella.amazingmazes.generators.labyrinth;

import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;

/**
 * A tiny graphical language based on Logo. Uses a turtle as in logo, and knows
 * only a few commands, but enough to draw paths through mazes.
 */
public interface GridLogoProgram {
	void run(Turtle turtle);

	Square getStartSquare(Grid.UtilityWrapper grid);
}