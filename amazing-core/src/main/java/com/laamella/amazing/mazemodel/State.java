package com.laamella.amazing.mazemodel;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface State {
	boolean hasState(Object state);

	void setState(Object newState, boolean hasOrNot);
}
