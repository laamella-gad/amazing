package com.laamella.amazingmazes.mazemodel.grid.implementation;

import com.laamella.amazingmazes.mazemodel.grid.Direction;

public class DirectionMap<T> {
    public T left;
    public T right;
    public T up;
    public T down;

    public DirectionMap() {
    }

    public DirectionMap(T up, T right, T down, T left) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public T get(Direction direction) {
        return switch (direction) {
            case LEFT -> left;
            case RIGHT -> right;
            case UP -> up;
            case DOWN -> down;
        };
    }
}
