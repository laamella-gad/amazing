package com.laamella.amazing.mazemodel;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface State {
	/**
	 * States that define the actual maze.
	 */
	public enum MazeDefinitionState {
		ENTRANCE, EXIT, OPEN
	}
	boolean hasState(Object state);

	void setState(Object newState, boolean hasOrNot);
}

