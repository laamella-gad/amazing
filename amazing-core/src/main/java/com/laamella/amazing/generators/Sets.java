package com.laamella.amazing.generators;

import java.util.*;

public class Sets<T> {

	private final List<Set<T>> sets;

	public Sets() {
		this.sets = new LinkedList<Set<T>>();
	}

	public void removeFromSet(final Set<T> set, final T element) {
		set.remove(element);
		if (set.size() == 0) {
			sets.remove(set);
		}
	}

	public void putInNewSet(final T element) {
		final Set<T> set = new HashSet<T>();
		set.add(element);
		sets.add(set);
	}

	public Set<T> findCorrespondingSet(final T element) {
		for (final Set<T> set : sets) {
			if (set.contains(element)) {
				return set;
			}
		}
		throw new RuntimeException("Bug in algorithm: element not found in any set, even though it was added previously");
	}

	public void unionSets(final Set<T> setA, final Set<T> setB) {
		setA.addAll(setB);
		sets.remove(setB);
	}

	public int size() {
		return sets.size();
	}

}
