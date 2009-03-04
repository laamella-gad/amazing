package com.laamella.amazing.generators.original;

import java.util.*;

import com.laamella.amazing.generators.MatrixMazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.State.ObservableObjectSetState;
import com.laamella.amazing.mazemodel.grid.Direction;
import com.laamella.amazing.mazemodel.matrix.Matrix;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;

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
		final Matrix.UtilityWrapper<ObservableObjectSetState> utilityMatrix = new Matrix.UtilityWrapper<ObservableObjectSetState>(matrix);
		final Position startPosition = randomizer.randomPosition(utilityMatrix.getSize());
		recurse(matrix, startPosition);
	}

	private void recurse(final StateMatrix matrix, final Position currentPosition) {
		matrix.get(currentPosition).setState(MazeDefinitionState.PASSAGE, true);

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
				if (matrix.get(newPosition).hasState(MazeDefinitionState.PASSAGE)) {
					amountOfSurroundingPassages++;
				}
			}
		}
		return amountOfSurroundingPassages <= 1;
	}
}
