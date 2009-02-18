package com.laamella.amazing.mazemodel.orthogonal;

public interface Wall extends State {
	void setOpened(boolean opened);

	void open();

	void close();

	boolean isOpen();

}
