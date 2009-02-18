package com.laamella.amazing.generators;

import java.util.Date;
import java.util.Random;

import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.Size;

public interface RandomGenerator {

	void reset();

	boolean chance(double d);

	public static class Default implements RandomGenerator {

		private final long seed;
		private Random random;

		public Default() {
			this.seed = new Date().getTime();
			reset();
		}

		public Default(final long seed) {
			this.seed = seed;
			reset();
		}

		public boolean chance(final double d) {
			return random.nextDouble() < 0.5;
		}

		public void reset() {
			random = new Random(seed);
		}

		public int random(final int max) {
			return random.nextInt(max);
		}

		public Position randomPosition(final Size size) {
			final int x = random(size.width);
			final int y = random(size.height);
			return new Position(x, y);
		}

		public int between(final int a, final int b) {
			return a+random((b-a)-1)+1;
		}
	}

	/**
	 * 
	 * @param size
	 * @return a position in an area of size size.
	 */
	Position randomPosition(Size size);

	/**
	 * @param max
	 * @return an integer between 0 and max, including 0, excluding max.
	 */
	int random(int max);

	/**
	 * @param a
	 * @param b
	 * @return an integer between a and b, excluding a and b.
	 */
	int between(int a, int b);
}
