package com.laamella.daedalousy.mazemodel;

public interface Matrix {
	public static final int CLEAR = 0;
	public static final int SOLID = 1;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param value
	 *            0 = clear, 1 = solid
	 */
	void set(int x, int y, int value);

	int get(int x, int y);

	int getWidth();

	int getHeight();
}
