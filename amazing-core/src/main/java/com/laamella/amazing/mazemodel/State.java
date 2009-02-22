package com.laamella.amazing.mazemodel;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface State {
	boolean hasState(Object state);

	void setState(Object newState, boolean hasOrNot);

	public static final Object VISITED = new Object();
	public static final Object ENTRANCE = new Object();
	public static final Object EXIT = new Object();
	public static final Object OPEN = new Object();
	public static final Object POSSIBLE_EXIT = new Object();
}
