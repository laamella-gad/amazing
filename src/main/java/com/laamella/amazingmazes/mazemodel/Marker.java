package com.laamella.amazingmazes.mazemodel;

/**
 * A state of anything in the maze. Used by generators.
 */
public interface Marker {
    static Marker singletonInstance() {
        return new Marker() {
        };
    }
}
