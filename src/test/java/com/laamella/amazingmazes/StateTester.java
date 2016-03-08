package com.laamella.amazingmazes;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.laamella.amazingmazes.mazemodel.State;
import com.laamella.amazingmazes.mazemodel.State.ObservableObjectSetState;

public class StateTester {
	@Test
	public void test1() {
		final Object key = new Object();
		final ObservableObjectSetState state = new State.ObservableObjectSetState();
		state.setState(key, true);
		assertTrue(state.hasState(key));
	}

	@Test
	public void test2() {
		final Object key = new Object();
		final ObservableObjectSetState state = new State.ObservableObjectSetState();
		state.setState(key, 15);
		assertTrue(state.hasState(key));
		assertEquals(15, state.getState(key).intValue());
	}
}
