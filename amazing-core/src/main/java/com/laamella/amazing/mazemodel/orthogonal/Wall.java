package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.mazemodel.graph.Edge;

public interface Wall extends Edge {
	void setOpened(boolean opened);

	void open();

	void close();

	boolean isOpen();
}
