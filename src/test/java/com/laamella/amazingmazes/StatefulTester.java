package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.Stateful;
import com.laamella.amazingmazes.mazemodel.Stateful.ObservableObjectSetState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatefulTester {
    @Test
    public void test1() {
        final MazeState key = MazeState.singletonInstance();
        final ObservableObjectSetState state = new Stateful.ObservableObjectSetState();
        state.setState(key, true);
        assertTrue(state.hasState(key));
    }

    @Test
    public void test2() {
        final MazeState key = MazeState.singletonInstance();
        final ObservableObjectSetState state = new Stateful.ObservableObjectSetState();
        state.setState(key, 15);
        assertTrue(state.hasState(key));
        assertEquals(15, state.getState(key).intValue());
    }
}
