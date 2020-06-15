package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.MazeState;
import com.laamella.amazingmazes.mazemodel.Stateful;
import com.laamella.amazingmazes.mazemodel.Stateful.ObservableObjectSetState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatefulTester {
    @Test
    void test1() {
        MazeState key = MazeState.singletonInstance();
        ObservableObjectSetState state = new Stateful.ObservableObjectSetState();
        state.setState(key, true);
        assertTrue(state.hasState(key));
    }

    @Test
    void test2() {
        MazeState key = MazeState.singletonInstance();
        ObservableObjectSetState state = new Stateful.ObservableObjectSetState();
        state.setState(key, 15);
        assertTrue(state.hasState(key));
        assertEquals(15, state.getState(key).intValue());
    }
}
