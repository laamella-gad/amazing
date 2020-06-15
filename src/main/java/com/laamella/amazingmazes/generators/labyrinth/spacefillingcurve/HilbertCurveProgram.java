package com.laamella.amazingmazes.generators.labyrinth.spacefillingcurve;

import com.laamella.amazingmazes.generators.labyrinth.GridLogoProgram;
import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * http://www.cut-the-knot.org/ctk/Mazes.shtml
 * <p>
 * <a href="http://www.cut-the-knot.org/do_you_know/hilbert.shtml">Cut the knot
 * article</a>
 * <p>
 * <a href="http://www.nio.ntnu.no/archive/2000_2001/2/b1.c">The Norsk
 * Informatikkolympiade for videregående skoler</a> has a rather bad
 * implementation.
 * <p>
 * Wikipedia has <a href="http://en.wikipedia.org/wiki/Hilbert_curve">the
 * article that this implementation is based on</a>.
 * <p>
 * <a href="http://tog.acm.org/GraphicsGems/gemsii/Hilbert.c">Graphics Gems 2
 * code</a>
 *
 * <pre>
 * -#################-
 * -#   #     #     #-
 * -### # ### # ### #-
 * -#   #   #   #   #-
 * -# ##### ##### ###-
 * -# #   # #   #   #-
 * -# # # # # # ### #-
 * -#   #   # #     #-
 * -######### #######-
 * -#   #   # #     #-
 * -# # # # # # ### #-
 * -# #   # #   #   #-
 * -# ##### ##### ###-
 * -#   #   #   #   #-
 * -### # ### # ### #-
 * -#   #     #     #-
 * -#################-
 * </pre>
 */
public class HilbertCurveProgram implements GridLogoProgram {
    private static final Logger log = LoggerFactory.getLogger(HilbertCurveProgram.class);

    private final int degree;
    private final boolean mirror;

    public HilbertCurveProgram(int degree, boolean mirror) {
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
     * @param rightTurning specifies the orientation of the maze: false: left-turning,
     * true: right-turning
     */
    public void drawCurve(Turtle turtle, int degree, boolean rightTurning) {
        if (degree == 0) {
            return;
        }
        log.debug("draw(" + turtle + ", " + degree + ", " + rightTurning + ")");

        right(turtle, rightTurning);
        drawCurve(turtle, degree - 1, !rightTurning);
        turtle.walk();
        left(turtle, rightTurning);
        drawCurve(turtle, degree - 1, rightTurning);
        turtle.walk();
        drawCurve(turtle, degree - 1, rightTurning);
        left(turtle, rightTurning);
        turtle.walk();
        drawCurve(turtle, degree - 1, !rightTurning);
        right(turtle, rightTurning);
    }

    @Override
    public void run(Turtle turtle) {
        drawCurve(turtle, degree, mirror);
    }

    @Override
    public Square getStartSquare(Grid grid) {
        return grid.getTopLeftSquare();
    }

}
