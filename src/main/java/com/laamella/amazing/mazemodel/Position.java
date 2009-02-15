package com.laamella.amazing.mazemodel;

public class Position {
	public final int x;
	public final int y;

	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Position move(final int dx, final int dy) {
		return new Position(x + dx, y + dy);
	}

	public Position scale(int multiplier) {
		return new Position(x * multiplier, y * multiplier);
	}
}
