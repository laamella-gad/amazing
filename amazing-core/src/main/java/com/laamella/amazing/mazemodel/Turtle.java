package com.laamella.amazing.mazemodel;

/**
 * A turtle (or cursor) to traverse a maze, opening passages while walking.
 */
public interface Turtle {
	void left();

	void right();

	void walk();

	/**
	 * 
	 * @return the angle the turtle is pointing (0..359)
	 */
	int getAngle();

	/**
	 * Set the angle the turtle is pointing to.
	 * 
	 * @param angle
	 *            (0..359)
	 */
	void setAngle(int angle);
}