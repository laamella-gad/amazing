package com.laamella.amazingmazes.mazemodel;

import com.laamella.amazingmazes.observe.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * Something that contains MazeStates. Used by generators.
 */
public interface Markable {
    boolean isMarked(Marker marker);

    void mark(Marker marker, boolean mustBeSet);

    default void mark(Marker marker) {
        mark(marker, true);
    }

    default void unmark(Marker marker) {
        mark(marker, false);
    }

    Integer getNumberMarker(Marker marker);

    void markNumber(Marker marker, int value);

    class ObservableMarkable extends Observable implements Markable {
        private final Map<Marker, Integer> markers = new HashMap<>(4);

        @Override
        public boolean isMarked(Marker marker) {
            return markers.containsKey(marker);
        }

        @Override
        public void mark(Marker marker, boolean mustBeSet) {
            boolean isAlreadyMarked = isMarked(marker);

            if (isAlreadyMarked && mustBeSet) {
                return;
            }
            if ((!isAlreadyMarked) && (!mustBeSet)) {
                return;
            }

            if (mustBeSet) {
                markers.put(marker, null);
            } else {
                markers.remove(marker);
            }
            setChanged();
            notifyObservers();
        }

        @Override
        public Integer getNumberMarker(Marker marker) {
            return markers.get(marker);
        }

        @Override
        public void markNumber(Marker marker, int value) {
            markers.put(marker, value);
        }

    }
}
