package com.laamella.amazingmazes.mazemodel;

import com.laamella.amazingmazes.observe.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface State {
    boolean hasState(Object state);

    void setState(Object newState, boolean mustBeSet);

    Integer getState(Object state);

    void setState(Object state, int value);

    class ObservableObjectSetState extends Observable implements State {
        private final Map<Object, Integer> states = new HashMap<>(4);

        @Override
        public boolean hasState(final Object state) {
            return states.containsKey(state);
        }

        @Override
        public void setState(final Object newState, final boolean mustBeSet) {
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
        public Integer getState(final Object state) {
            return states.get(state);
        }

        @Override
        public void setState(final Object state, final int value) {
            states.put(state, value);
        }

    }
}
