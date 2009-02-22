package com.laamella.amazing.mazemodel.matrix.implementation;

import java.util.ArrayList;
import java.util.List;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.matrix.Matrix;

public abstract class ListMatrix<T> implements Matrix<T> {
	private final List<T> matrix;
	private final Size size;

	public ListMatrix(final Size size) {
		this.size = size;
		this.matrix = new ArrayList<T>();
		for (int i = 0; i < size.area; i++) {
			matrix.add(newItem());
		}
	}

	protected abstract T newItem();

	public T get(Position position) {
		if (position.x >= size.width) {
			throw new IndexOutOfBoundsException("x " + position.x + " >= " + size.width);
		}
		if (position.y >= size.height) {
			throw new IndexOutOfBoundsException("y " + position.y + " >= " + size.height);
		}
		if (position.x < 0) {
			throw new IndexOutOfBoundsException("x " + position.x + " < 0");
		}
		if (position.y < 0) {
			throw new IndexOutOfBoundsException("y " + position.y + " < 0");
		}
		return matrix.get(position.x + position.y * size.width);
	}

	public Size getSize() {
		return size;
	}

	public void set(Position position, T value) {
		matrix.set(position.x + position.y * size.width, value);
	}

}
