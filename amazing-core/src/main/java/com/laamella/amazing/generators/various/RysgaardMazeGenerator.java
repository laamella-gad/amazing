package com.laamella.amazing.generators.various;

import java.util.*;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.MazeGeneratorTester;
import com.laamella.amazing.generators.MatrixMazeGenerator;
import com.laamella.amazing.generators.Randomizer;
import com.laamella.amazing.mazemodel.MazeDefinitionState;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.grid.Direction;
import com.laamella.amazing.mazemodel.matrix.implementation.StateMatrix;

/**
 * <a href="http://www.glimt.dk/code/labyrinth.htm">A nice algorithm</a>
 */
public class RysgaardMazeGenerator implements MatrixMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(RysgaardMazeGenerator.class);

	private final Randomizer randomizer;

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
		for (final Position offset : EIGHT_OFFSETS_AROUND_CELL) {
			final Position position = currentPosition.move(offset);
			if (position.isInside(matrix.getSize())) {
				if (!matrix.get(position).hasState(MazeDefinitionState.PASSAGE)) {
					check(possiblePositions, matrix, position);
				}
			}
		}
	}

	private void check(final Set<Position> possiblePositions, final StateMatrix matrix, final Position position) {
		for (final Direction direction : Direction.values()) {
			checkOffset(possiblePositions, matrix, position, direction.getMove());
		}
	}

	private void checkOffset(final Set<Position> possiblePositions, final StateMatrix matrix, final Position positionToCheck, final Position offset) {
		// Private Sub Generate_CheckOffset(X As Long, Y As Long, dx As Long, dy
		// As Long)
		// Dim IsValid As Boolean
		// Dim tmpx As Long, tmpy As Long
		//		  
		// ' check for coordinates on boundaries
		// If X >= Width Or X <= 1 Or Y >= Height Or Y <= 1 Then Exit Sub
		//		  
		// ' check if cell + offset is still within labyrinth boundaries
		// If Not IsCoordinateValid(X + dx, Y + dy) Then Exit Sub
		//		  
		// ' make backup of original coordinates
		// tmpx = X
		// tmpy = Y
		//		  
		// ' check neighbour cell vertical or horisontal
		final Position neighbour = positionToCheck.move(offset);
		boolean isValid = isWall(matrix, neighbour) && isWall(matrix, neighbour.move(offset.negate())) && isWall(matrix, neighbour.move(offset));
		// tmpy = tmpy + dy
		// tmpx = tmpx + dx
		// IsValid = Cell(tmpx, tmpy) = CC_WALL And Cell(tmpx - dy, tmpy - dx) =
		// CC_WALL And Cell(tmpx + dy, tmpy + dx) = CC_WALL
		if (isValid) {
		// If Not IsValid Then Exit Sub
		//		  
		// If IsCoordinateValid(tmpx + dx, tmpy + dy) Then
		// ' check neighbours neighbour cell vertical or horisontal
		// tmpy = tmpy + dy
		// tmpx = tmpx + dx
		final Position neighbourOfNeighbour = neighbour.move(offset);
		isValid = isWall(matrix, neighbourOfNeighbour) && isWall(matrix, neighbourOfNeighbour.move(offset.negate()))
				&& isWall(matrix, neighbourOfNeighbour.move(offset));
		// IsValid = Cell(tmpx, tmpy) = CC_WALL And Cell(tmpx - dy, tmpy - dx) =
		// CC_WALL And Cell(tmpx + dy, tmpy + dx) = CC_WALL
		// End If
		//		  
		// ' add position with offset as a valid position
		// If IsValid Then
		// Dim newpos As Long
		// Dim found As Boolean
		// Dim key As String
		// Dim pos As Variant
		//		    
		// ' convert coordinates to position and get key
		// Coord2Pos X + dx, Y + dy, newpos
		if(isValid){
		if (!possiblePositions.contains(neighbour)) {
			possiblePositions.add(neighbour);
		}
		}
		}

		// key = Pos2Key(newpos)
		//		    
		// ' check if position already present in collection of positions
		// On Error Resume Next
		// pos = colPositions.Item(key)
		// found = (Err.Number = 0)
		// On Error GoTo 0
		//		    
		// ' add to collection of positions if not already present
		// If Not found Then colPositions.Add newpos, key
		// End If
		// End Sub

	}

	private boolean isWall(final StateMatrix matrix, final Position position) {
		log.debug("Checking wallness of position " + position);
		if (position.isInside(matrix.getSize())) {
			return !matrix.get(position).hasState(MazeDefinitionState.PASSAGE);
		}
		return false;
	}

	private static final List<Position> EIGHT_OFFSETS_AROUND_CELL = Arrays.asList(//
			new Position(-1, -1), new Position(0, -1), new Position(1, -1), //
			new Position(-1, 0), new Position(1, 0), //
			new Position(-1, 1), new Position(0, 1), new Position(1, 1)//
			);

	private void removeInvalidatedPositions(Set<Position> possiblePositions, Position currentPosition) {
		for (final Position offset : EIGHT_OFFSETS_AROUND_CELL) {
			possiblePositions.remove(currentPosition.move(offset));
		}
	}
}
