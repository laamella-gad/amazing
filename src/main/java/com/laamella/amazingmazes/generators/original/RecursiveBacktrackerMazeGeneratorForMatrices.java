package com.laamella.amazingmazes.generators.original;

import com.laamella.amazingmazes.generators.MatrixMazeGenerator;
import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.matrix.implementation.MarkableMatrix;

import java.util.EnumSet;
import java.util.Set;

import static com.laamella.amazingmazes.mazemodel.MazeDefinitionMarker.*;

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

    public RecursiveBacktrackerMazeGeneratorForMatrices(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    @Override
    public void generateMaze(MarkableMatrix matrix) {
        Position startPosition = randomizer.randomPosition(matrix.getSize());
        recurse(matrix, startPosition);
    }

    private void recurse(MarkableMatrix matrix, Position currentPosition) {
        matrix.get(currentPosition).mark(PASSAGE);

        Set<Direction> directionsToTry = EnumSet.allOf(Direction.class);
        for (Direction direction : randomizer.shuffle(directionsToTry)) {
            Position newPosition = currentPosition.move(direction.getMove());
            if (newPosition.isInside(matrix.getSize())) {
                if (notNextToAnotherPassage(matrix, newPosition)) {
                    recurse(matrix, newPosition);
                }
            }
        }
    }

    private boolean notNextToAnotherPassage(MarkableMatrix matrix, Position position) {
        int amountOfSurroundingPassages = 0;
        for (Direction direction : EnumSet.allOf(Direction.class)) {
            Position newPosition = position.move(direction.getMove());
            if (newPosition.isInside(matrix.getSize())) {
                if (matrix.get(newPosition).isMarked(PASSAGE)) {
                    amountOfSurroundingPassages++;
                }
            }
        }
        return amountOfSurroundingPassages <= 1;
    }
}
