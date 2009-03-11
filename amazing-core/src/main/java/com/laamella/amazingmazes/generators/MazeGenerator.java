package com.laamella.amazingmazes.generators;

/**
 * A maze generator takes a maze model, and renders a maze in it according to
 * some algorithm.
 */
public interface MazeGenerator {
	public static Object VISITED_WHILE_GENERATING = new Object();
	public static Object POSSIBLE_EXIT = new Object();
}
