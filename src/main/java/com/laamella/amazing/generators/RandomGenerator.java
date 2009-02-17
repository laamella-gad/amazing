package com.laamella.amazing.generators;

import java.util.Random;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;
import com.laamella.amazing.mazemodel.orthogonal.Square;

public interface RandomGenerator {

	void reset();

	boolean chance(double d);

	public static class Default implements RandomGenerator {

		private final int seed;
		private Random random;

		public Default(final int seed) {
			this.seed = seed;
			reset();
		}

		public boolean chance(double d) {
			return random.nextDouble() < 0.5;
		}

		public void reset() {
			random = new Random(seed);
		}

		public int random(int max) {
			return random.nextInt(max);
		}

		public Position randomPosition(Size size) {
			final int x = random(size.width);
			final int y = random(size.height);
			return new Position(x, y);
		}
	}

	Position randomPosition(Size size);

	int random(int max);
}
