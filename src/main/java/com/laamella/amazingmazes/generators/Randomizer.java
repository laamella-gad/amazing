package com.laamella.amazingmazes.generators;

import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.Size;

import java.util.*;

public interface Randomizer {
    /**
     * Restart the random number series.
     */
    void reset();

    /**
     * @param d 0.anything
     * @return true with a chance of d, else false.
     */
    boolean chance(double d);

    /**
     * @return a position in an area of size size.
     */
    Position randomPosition(Size size);

    /**
     * @return an integer between 0 and max, including 0, excluding max.
     */
    int random(int max);

    /**
     * @return an integer between a and b, excluding a and b.
     */
    int between(int a, int b);

    <T> List<T> shuffle(Collection<T> collection);

    <T> T pickOne(Collection<T> collection);

    class Default implements Randomizer {

        private final long seed;
        private Random random;

        public Default() {
            this.seed = new Date().getTime();
            reset();
        }

        public Default(long seed) {
            this.seed = seed;
            reset();
        }

        @Override
        public boolean chance(double d) {
            return random.nextDouble() < d;
        }

        @Override
        public void reset() {
            random = new Random(seed);
        }

        @Override
        public int random(int max) {
            return random.nextInt(max);
        }

        @Override
        public Position randomPosition(Size size) {
            int x = random(size.width);
            int y = random(size.height);
            return new Position(x, y);
        }

        @Override
        public int between(int a, int b) {
            return a + random(b - a);
        }

        @Override
        public <T> List<T> shuffle(Collection<T> collection) {
            List<T> list = new ArrayList<>(collection);
            Collections.shuffle(list);
            return list;
        }

        @Override
        public <T> T pickOne(Collection<T> collection) {
            if (collection.size() > 0) {
                return shuffle(collection).get(0);
            }
            return null;
        }

    }

}
