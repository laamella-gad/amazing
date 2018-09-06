package com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve;

import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgram;
import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Grid.UtilityWrapper;
import com.laamella.amazingmazes.mazemodel.grid.Square;

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
