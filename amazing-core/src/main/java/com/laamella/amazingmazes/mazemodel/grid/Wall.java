package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.mazemodel.graph.Edge;

public interface Wall extends Edge {
	void setOpened(boolean opened);

	void open();

	void close();

	boolean isOpen();
}
