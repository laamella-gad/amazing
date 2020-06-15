package com.laamella.amazingmazes.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A datastructure that keeps track of a set of sets.
 *
 * @param <T> The datatype inside the sets.
 */
public class Sets<T> {

    private final List<Set<T>> sets;

    public Sets() {
        this.sets = new ArrayList<>();
    }

    /**
     * Remove an element from a set. If the set ends up empty, it is deleted.
     *
     * @param set the set to delete the element from.
     * @param element the element to remove.
     */
    public void removeFromSet(Set<T> set, T element) {
        set.remove(element);
        if (set.size() == 0) {
            sets.remove(set);
        }
    }

    /**
     * Create a new set containing only the passed element.
     *
     * @param element the element to put in the new set.
     * @return the new set.
     */
    public Set<T> putInNewSet(T element) {
        Set<T> set = new HashSet<>();
        set.add(element);
        sets.add(set);
        return set;
    }

    /**
     * @param element the element to look for. It must be in on of the sets.
     * @return the set which contains element.
     */
    public Set<T> findSetContaining(T element) {
        for (Set<T> set : sets) {
            if (set.contains(element)) {
                return set;
            }
        }
        throw new RuntimeException("Bug in algorithm: element " + element + " not found in any set, even though it was added previously");
    }

    /**
     * Put set B in set A, the delete set B.
     */
    public Set<T> unionSets(Set<T> setA, Set<T> setB) {
        setA.addAll(setB);
        sets.remove(setB);
        return setA;
    }

    /**
     * @return the amount of sets inside this set of sets.
     */
    public int size() {
        return sets.size();
    }

}
