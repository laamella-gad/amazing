package com.laamella.amazing.mazemodel.orthogonal;

public interface State {
	boolean hasState(int state);

	void setState(int newState, boolean hasOrNot);

}
