package com.laamella.amazing.mazemodel.orthogonal;

import com.laamella.amazing.generators.RandomGenerator;
import com.laamella.amazing.mazemodel.Position;

public enum Direction {
	UP, DOWN, RIGHT, LEFT;

	private final static DirectionMap<Direction> TURN_LEFT_MAP;
	private final static DirectionMap<Direction> TURN_RIGHT_MAP;
	private final static DirectionMap<Position> MOVE_MAP;
	private final static Direction[] RANDOM_MAP;

	static {
		TURN_LEFT_MAP = new DirectionMap<Direction>(LEFT, UP, RIGHT, DOWN);
		TURN_RIGHT_MAP = new DirectionMap<Direction>(RIGHT, DOWN, LEFT, UP);
		MOVE_MAP = new DirectionMap<Position>(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0));
		RANDOM_MAP = new Direction[] { UP, RIGHT, DOWN, LEFT };
	}

	Direction turnLeft() {
		return TURN_LEFT_MAP.get(this);
	}

	Direction turnRight() {
		return TURN_RIGHT_MAP.get(this);
	}

	Position getMove() {
		return MOVE_MAP.get(this);
	}

	public static Direction random(RandomGenerator randomGenerator) {
		return RANDOM_MAP[randomGenerator.random(4)];
	}
	
	@Override
	public String toString() {
		return name();
	}
}