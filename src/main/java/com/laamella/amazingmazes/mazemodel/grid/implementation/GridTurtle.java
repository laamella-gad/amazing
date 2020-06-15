package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;

public class GridTurtle implements Turtle {
    private static final Logger log = LoggerFactory.getLogger(GridTurtle.class);

    private Square currentSquare;
    private Direction direction;

    public GridTurtle(final Square currentSquare, final Direction direction) {
        this.currentSquare = currentSquare;
        this.direction = direction;
    }

    @Override
    public void right() {
        log.debug("Right 90");
        direction = direction.turnRight();
    }

    @Override
    public void left() {
        log.debug("Left 90");
        direction = direction.turnLeft();
    }

    @Override
    public void walk() {
        log.debug("Walk");
        currentSquare.getWall(direction).setOpened(true);
        currentSquare = currentSquare.getSquare(direction);
    }

    @Override
    public int getAngle() {
        return switch (direction) {
            case DOWN -> 180;
            case UP -> 0;
            case LEFT -> 270;
            case RIGHT -> 90;
        };
    }

    @Override
    public void setAngle(final int angle) {
        this.direction = switch (angle) {
            case 0 -> UP;
            case 90 -> RIGHT;
            case 180 -> DOWN;
            case 270 -> LEFT;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return "Turtle(" + currentSquare.getPosition().toString() + "," + getAngle() + ")";
    }
}
