package com.laamella.amazingmazes.mazemodel;

import com.laamella.amazingmazes.observe.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * Something that contains MazeStates. Used by generators.
 */
public interface Stateful {
    boolean hasState(MazeState state);

    void setState(MazeState newState, boolean mustBeSet);

    Integer getState(MazeState state);

    void setState(MazeState state, int value);

    class ObservableObjectSetState extends Observable implements Stateful {
        private final Map<MazeState, Integer> states = new HashMap<>(4);

        @Override
        public boolean hasState(MazeState state) {
            return states.containsKey(state);
        }

        @Override
        public void setState(MazeState newState, boolean mustBeSet) {
            boolean alreadyHas = hasState(newState);

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
        public Integer getState(MazeState state) {
            return states.get(state);
        }

        @Override
        public void setState(MazeState state, int value) {
            states.put(state, value);
        }

    }
}
