package com.laamella.amazingmazes.observe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Observable {
    private boolean changed = false;
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    public synchronized void addObserver(Observer o) {
        Objects.requireNonNull(o);
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void notifyObservers() {
        if (changed) {
            observers.forEach(Observer::update);
            changed = false;
        }
    }

    public synchronized void deleteObservers() {
        observers.clear();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    public boolean hasChanged() {
        return changed;
    }
}
