package com.laamella.daedalousy.mazemodel.orthogonal;

public interface Wall extends State {
	void setSolid(boolean solid);

	boolean isSolid();

}
