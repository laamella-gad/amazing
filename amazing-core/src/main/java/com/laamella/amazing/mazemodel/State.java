package com.laamella.amazing.mazemodel;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

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

	public static class ObservableObjectSetState extends Observable implements State {
		private final Set<Object> states = new HashSet<Object>(4);

		@Override
		public boolean hasState(final Object state) {
			return states.contains(state);
		}

		@Override
		public void setState(Object newState, boolean hasOrNot) {
			final boolean alreadyHas = hasState(newState);

			if (alreadyHas && hasOrNot) {
				return;
			}
			if ((!alreadyHas) && (!hasOrNot)) {
				return;
			}

			if (hasOrNot) {
				states.add(newState);
			} else {
				states.remove(newState);
			}
			setChanged();
			notifyObservers();
		}

	}
}
