package com.laamella.daedalousy.mazemodel;

public class TileMapToSquareGridAdapter implements SquareGrid {

	private final SquareTileMap tileMap;

	public TileMapToSquareGridAdapter(final SquareTileMap tileMap) {
		this.tileMap = tileMap;
	}

	public boolean isMazeWallSolid(int x, int y, int wall) {
		return false;
	}

	public void setMazeWall(int x, int y, Wall wall, boolean solid) {
		final int tx = x * 2 + 1;
		final int ty = y * 2 + 1;
		switch (wall) {
		case EAST:
			tileMap.setMazeTile(tx + 1, ty, solid);
			break;
		case NORTH:
			tileMap.setMazeTile(tx, ty - 1, solid);
			break;
		case SOUTH:
			tileMap.setMazeTile(tx, ty + 1, solid);
			break;
		case WEST:
			tileMap.setMazeTile(tx - 1, ty, solid);
			break;

		}
		tileMap.setMazeTile(tx + 1, ty + 1, true);
	}

	public int getHeightInSquares() {
		return tileMap.getHeight() / 2 -2;
	}

	public int getHeightInWalls() {
		return getHeightInSquares() + 1;
	}

	public int getWidthInSquares() {
		return tileMap.getWidth() / 2 -2;
	}

	public int getWidthInWalls() {
		return getWidthInSquares() + 1;
	}

}




