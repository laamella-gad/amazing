package com.laamella.amazingmazes.mazemodel.grid;

import java.util.List;

/**
 * Interface to receive data from progressive maze algorithms. Receives one row
 * at a time, and is called back to ask whether the algorithm is done.
 */
public interface RowGenerator {
	/**
	 * The implementor is expected to signal when the generator is supposed to
	 * stop creating new rows.
	 * 
	 * @return the number of rows the algorithm is still expected to generate.
	 *         If the size is indefinite, use a large number until the generator
	 *         should finish.
	 */
	int rowsToGo();

	/**
	 * Called by the generator when it is done with the previous row. The first
	 * row is at the top of the maze, the following rows are expected to be
	 * below it.
	 * 
	 * @return the next row, as a list of squares. Make sure every square has
	 *         four walls around it, that the walls in between squares are the
	 *         same, and that the walls on the top of the squares are the ones
	 *         that were on the bottom of the squares of the row before.
	 */
	List<Square> nextRow();
}