package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.Turtle;
import com.laamella.amazingmazes.mazemodel.grid.Direction;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridTurtle implements Turtle {
    private static Logger log = LoggerFactory.getLogger(GridTurtle.class);

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
        switch (direction) {
            case DOWN:
                return 180;
            case UP:
                return 0;
            case LEFT:
                return 270;
            case RIGHT:
                return 90;
        }
        throw new IllegalStateException();
    }

    @Override
    public void setAngle(final int direction) {
        switch (direction) {
            case 0:
                this.direction = Direction.UP;
                return;
            case 90:
                this.direction = Direction.RIGHT;
                return;
            case 180:
                this.direction = Direction.DOWN;
                return;
            case 270:
                this.direction = Direction.LEFT;
                return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Turtle(" + currentSquare.getPosition().toString() + "," + getAngle() + ")";
    }
}
