package com.laamella.amazingmazes.generators.original;

import com.laamella.amazingmazes.generators.MatrixMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.StateMatrix;

import java.util.EnumSet;
import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionState.*;

/**
 * A recursive backtracker that works directly on matrixes. It's not perfect,
 * since it will often leave parts of the matrix inaccessible.
 * <ul>
 * <li>Does not set an entrance
 * <li>Does not set an exit
 * <li>Does not require an entrance
 * </ul>
 */
public class RecursiveBacktrackerMazeGeneratorForMatrices implements MatrixMazeGenerator {
    private final Randomizer randomizer;

    public RecursiveBacktrackerMazeGeneratorForMatrices(final Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    @Override
    public void generateMaze(final StateMatrix matrix) {
        final Position startPosition = randomizer.randomPosition(matrix.getSize());
        recurse(matrix, startPosition);
    }

    private void recurse(final StateMatrix matrix, final Position currentPosition) {
        matrix.get(currentPosition).setState(PASSAGE, true);

        final Set<Direction> directionsToTry = EnumSet.allOf(Direction.class);
        for (final Direction direction : randomizer.shuffle(directionsToTry)) {
            final Position newPosition = currentPosition.move(direction.getMove());
            if (newPosition.isInside(matrix.getSize())) {
                if (notNextToAnotherPassage(matrix, newPosition)) {
                    recurse(matrix, newPosition);
                }
            }
        }
    }

    private boolean notNextToAnotherPassage(final StateMatrix matrix, final Position position) {
        int amountOfSurroundingPassages = 0;
        for (final Direction direction : EnumSet.allOf(Direction.class)) {
            final Position newPosition = position.move(direction.getMove());
            if (newPosition.isInside(matrix.getSize())) {
                if (matrix.get(newPosition).hasState(PASSAGE)) {
                    amountOfSurroundingPassages++;
                }
            }
        }
        return amountOfSurroundingPassages <= 1;
    }
}
