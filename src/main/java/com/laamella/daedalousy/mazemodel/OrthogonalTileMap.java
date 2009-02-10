package com.laamella.daedalousy.mazemodel;

public interface OrthogonalTileMap {
	void setMazeTile(int x, int y, boolean solid);

	boolean isMazeTileSolid(int x, int y);

	int getWidth();

	int getHeight();
}
