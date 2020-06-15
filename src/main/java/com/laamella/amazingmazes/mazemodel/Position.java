package com.laamella.amazingmazes.mazemodel;

/**
 * A position in 2D space.
 */
public class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position move(Position position) {
        return new Position(x + position.x, y + position.y);
    }

    public Position scale(int multiplier) {
        return new Position(x * multiplier, y * multiplier);
    }

    public Position negate() {
        return new Position(-x, -y);
    }

    public Position switchXY() {
        return new Position(y, x);
    }

    public boolean isInside(Size size) {
        return x >= 0 && y >= 0 && x < size.width && y < size.height;
    }

    @Override
    public String toString() {
        return "" + x + "," + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).x == x && ((Position) obj).y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x * 17 + y * 37373737;
    }
}
