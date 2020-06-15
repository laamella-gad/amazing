package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;
import com.laamella.amazingmazes.mazemodel.Stateful;

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
    Stateful getSquareState(Position position);

    Stateful getWallState(Position position, boolean horizontal);

    Size getSize();
}
