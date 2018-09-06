package com.laamella.amazingmazes.mazemodel.grid;

import com.laamella.amazingmazes.generators.Randomizer;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.implementation.DirectionMap;

// TODO represent directions as state in degrees on the graph edges.
public enum Direction {
    UP, DOWN, RIGHT, LEFT;

    private final static DirectionMap<Direction> TURN_LEFT_MAP;
    private final static DirectionMap<Direction> TURN_RIGHT_MAP;
    private final static DirectionMap<Position> MOVE_MAP;
    private final static Direction[] RANDOM_MAP;

    static {
        TURN_LEFT_MAP = new DirectionMap<>(LEFT, UP, RIGHT, DOWN);
        TURN_RIGHT_MAP = new DirectionMap<>(RIGHT, DOWN, LEFT, UP);
        MOVE_MAP = new DirectionMap<>(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0));
        RANDOM_MAP = new Direction[]{UP, RIGHT, DOWN, LEFT};
    }

    public Direction turnLeft() {
        return TURN_LEFT_MAP.get(this);
    }

    public Direction turnRight() {
        return TURN_RIGHT_MAP.get(this);
    }

    public Position getMove() {
        return MOVE_MAP.get(this);
    }

    public static Direction random(Randomizer randomGenerator) {
        return RANDOM_MAP[randomGenerator.random(4)];
    }

    @Override
    public String toString() {
        return name();
    }
}