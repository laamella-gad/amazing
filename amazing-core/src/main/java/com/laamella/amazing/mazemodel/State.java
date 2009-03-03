package com.laamella.amazing.mazemodel;

import java.util.*;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface State {
	boolean hasState(Object state);

	void setState(Object newState, boolean mustBeSet);

	int getState(Object state);

	void setState(Object state, int value);

	public static class ObservableObjectSetState extends Observable implements State {
		private final Map<Object, Integer> states=new HashMap<Object, Integer>(4);

		@Override
		public boolean hasState(final Object state) {
			return states.containsKey(state);
		}

		@Override
		public void setState(Object newState, boolean mustBeSet) {
			final boolean alreadyHas = hasState(newState);

			if (alreadyHas && mustBeSet) {
				return;
			}
			if ((!alreadyHas) && (!mustBeSet)) {
				return;
			}

			if (mustBeSet) {
				states.put(newState, null);
			} else {
				states.remove(newState);
			}
			setChanged();
			notifyObservers();
		}

		@Override
		public int getState(Object state) {
			return (int)states.get(state);
		}

		@Override
		public void setState(Object state, int value) {
			states.put(state, value);
		}

	}
}
