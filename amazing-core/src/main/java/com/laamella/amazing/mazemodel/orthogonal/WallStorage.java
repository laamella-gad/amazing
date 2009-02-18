package com.laamella.amazing.mazemodel.orthogonal;

public interface WallStorage extends State {
	void setOpened(boolean opened);

	boolean isOpen();
}
