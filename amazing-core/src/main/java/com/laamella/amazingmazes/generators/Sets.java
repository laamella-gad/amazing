package com.laamella.amazingmazes.generators;

import java.util.*;

/**
 * A datastructure that keeps track of a set of sets.
 * 
 * @param <T>
 *            The datatype inside the sets.
 */
public class Sets<T> {

	private final List<Set<T>> sets;

	public Sets() {
		this.sets = new LinkedList<Set<T>>();
	}

	/**
	 * Remove an element from a set. If the set ends up empty, it is deleted.
	 * 
	 * @param set
	 *            the set to delete the element from.
	 * @param element
	 *            the element to remove.
	 */
	public void removeFromSet(final Set<T> set, final T element) {
		set.remove(element);
		if (set.size() == 0) {
			sets.remove(set);
		}
	}

	/**
	 * Create a new set containing only the passed element.
	 * 
	 * @param element
	 *            the element to put in the new set.
	 * @return the new set.
	 */
	public Set<T> putInNewSet(final T element) {
		final Set<T> set = new HashSet<T>();
		set.add(element);
		sets.add(set);
		return set;
	}

	/**
	 * @param element
	 *            the element to look for. It must be in on of the sets.
	 * @return the set which contains element.
	 */
	public Set<T> findSetContaining(final T element) {
		for (final Set<T> set : sets) {
			if (set.contains(element)) {
				return set;
			}
		}
		throw new RuntimeException("Bug in algorithm: element " + element + " not found in any set, even though it was added previously");
	}

	/**
	 * Put set B in set A, the delete set B.
	 * 
	 * @param setA
	 * @param setB
	 * @return setA
	 */
	public Set<T> unionSets(final Set<T> setA, final Set<T> setB) {
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
