package com.laamella.amazingmazes.observe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The old observer pattern from java.util. It was deprecated by Oracle, so the essence has been copied here.
 */
public class Observable {
    private boolean changed = false;
    private final List<Observer> observers = new ArrayList<>();

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
