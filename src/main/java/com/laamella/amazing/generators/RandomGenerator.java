package com.laamella.amazing.generators;

import java.util.Random;

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

	}
}
