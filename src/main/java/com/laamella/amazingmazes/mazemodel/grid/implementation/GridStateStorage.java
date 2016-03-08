package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.*;

/**
 * <pre>
 *       -------  &lt;-- horizontal wall x,y 
 *    |  +-----+   |
 *    |  |squar|   |  &lt;-- vertical wall x+1,y
 *    |  | x,y |   |
 *    |  +-----+   |
 *    &circ;  -------  &lt;-- horizontal wall x,y+1
 *    |
 *    vertical wall x,y
 * </pre>
 */
public interface GridStateStorage {
	State getSquareState(Position position);

	State getWallState(Position position, boolean horizontal);

	Size getSize();
}
