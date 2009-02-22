package com.laamella.amazing.mazemodel.orthogonal.implementation;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;

/**
 * <pre>
 *       -------  <-- horizontal wall x,y 
 *
 *    |  +-----+   |
 *    |  |squar|   |  <-- vertical wall x+1,y
 *    |  | x,y |   |
 *    |  +-----+   |
 *
 *    ^  -------  <-- horizontal wall x,y+1
 *    |
 *    vertical wall x,y
 * </pre>
 */
public interface GridStorageFactory {
	SquareStorage createSquareStorage(Position position);

	WallStorage createHorizontalWallStorage(Position position);

	WallStorage createVerticalWallStorage(Position position);
	
	Size getSize();
}
