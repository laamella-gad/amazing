package com.laamella.amazing.mazemodel.orthogonal;

public interface State {
	boolean hasState(Object state);

	void setState(Object newState, boolean hasOrNot);

}
