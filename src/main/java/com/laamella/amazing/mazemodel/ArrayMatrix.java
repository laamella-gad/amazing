package com.laamella.amazing.mazemodel;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayMatrix<T> implements Matrix<T> {
	private final List<T> matrix;
	private final Size size;

	public ArrayMatrix(Size size) {
		this.size = size;
		this.matrix = new ArrayList<T>();
		for (int i = 0; i < size.area; i++) {
			matrix.add(newItem());
		}
	}

	protected abstract T newItem();

	public T get(Position position) {
		return matrix.get(position.x + position.y * size.width);
	}

	public Size getSize() {
		return size;
	}

	public void set(Position position, T value) {
		matrix.set(position.x + position.y * size.width, value);
	}

}
