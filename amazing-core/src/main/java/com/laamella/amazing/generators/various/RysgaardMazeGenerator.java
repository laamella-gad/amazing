package com.laamella.amazing.generators.various;

import java.util.*;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.MatrixMazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.grid.Direction;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;

/**
 * <a href="http://www.glimt.dk/code/labyrinth.htm">An algorithm by some guy.</a>
 */
public class RysgaardMazeGenerator implements MatrixMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(RysgaardMazeGenerator.class);

	private final Randomizer randomizer;

	private static final List<Position> EIGHT_OFFSETS_AROUND_CELL = Arrays.asList(//
			new Position(-1, -1), new Position(0, -1), new Position(1, -1), //
			new Position(-1, 0), new Position(1, 0), //
			new Position(-1, 1), new Position(0, 1), new Position(1, 1)//
			);

	public RysgaardMazeGenerator(final Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	@Override
	public void generateMaze(StateMatrix matrix) {
		// 1. Fill the entire labyrinth with walls

		// 2. Find a random beginning position, store it as a possible position
		// and make it the current position
		final Set<Position> possiblePositions = new HashSet<Position>();
		Position currentPosition = randomizer.randomPosition(matrix.getSize());
		possiblePositions.add(currentPosition);

		// 3. Mark the current position as a path in the labyrinth and remove it
		// from the list of possible positions
		do {
			matrix.get(currentPosition).setState(MazeDefinitionState.PASSAGE, true);
			possiblePositions.remove(currentPosition);

			// 4. Remove surrounding positions invalidated by the current
			// position
			// from the list of possible positions
			removeInvalidatedPositions(possiblePositions, currentPosition);

			// 5. Add left, right, upper and lower cell positions created by the
			// newly used position to the list of possible positions
			addNewCellPositions(matrix, possiblePositions, currentPosition);

			// 6. If more possible positions are left then get the next random
			// possible position and restart from point 3
			currentPosition = randomizer.pickOne(possiblePositions);
		} while (possiblePositions.size() > 0);
	}

	private void addNewCellPositions(final StateMatrix matrix, final Set<Position> possiblePositions, final Position currentPosition) {
		for (final Direction direction : Direction.values()) {
			checkOffset(possiblePositions, matrix, currentPosition, direction.getMove());
		}
	}

	/**
	 * Check a 2x3 or 3x2 area in the specified direction for any non-walls
	 */
	private void checkOffset(final Set<Position> possiblePositions, final StateMatrix matrix, final Position positionToCheck, final Position direction) {
		final Position neighbour = positionToCheck.move(direction);
		if (isWall(matrix, neighbour) && isWall(matrix, neighbour.move(direction.negate().switchXY())) && isWall(matrix, neighbour.move(direction.switchXY()))) {
			final Position neighbourOfNeighbour = neighbour.move(direction);
			if (isWall(matrix, neighbourOfNeighbour) && isWall(matrix, neighbourOfNeighbour.move(direction.switchXY().negate()))
					&& isWall(matrix, neighbourOfNeighbour.move(direction.switchXY()))) {
				if (!possiblePositions.contains(neighbour)) {
					possiblePositions.add(neighbour);
				}
			}
		}
	}

	private boolean isWall(final StateMatrix matrix, final Position position) {
		log.debug("Checking wallness of position " + position);
		if (position.isInside(matrix.getSize())) {
			return !matrix.get(position).hasState(MazeDefinitionState.PASSAGE);
		}
		return false;
	}

	private void removeInvalidatedPositions(Set<Position> possiblePositions, Position currentPosition) {
		for (final Position offset : EIGHT_OFFSETS_AROUND_CELL) {
			possiblePositions.remove(currentPosition.move(offset));
		}
	}
}
