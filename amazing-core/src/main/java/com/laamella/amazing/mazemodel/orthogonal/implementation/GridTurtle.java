package com.laamella.amazing.mazemodel.orthogonal.implementation;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.mazemodel.Turtle;
import com.laamella.amazing.mazemodel.orthogonal.Direction;
import com.laamella.amazing.mazemodel.orthogonal.Square;

public class GridTurtle implements Turtle {
	private static final SimpleLogger log = new SimpleLogger(GridTurtle.class);

	private Square currentSquare;
	private Direction direction;

	public GridTurtle(final Square currentSquare, final Direction direction) {
		this.currentSquare = currentSquare;
		this.direction = direction;
	}

	public void right() {
		log.debug("Right 90");
		direction = direction.turnRight();
	}

	public void left() {
		log.debug("Left 90");
		direction = direction.turnLeft();
	}

	public void forward() {
		log.debug("Step");
		currentSquare.getWall(direction).setOpened(true);
		currentSquare = currentSquare.getSquare(direction);
	}
}
