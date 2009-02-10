package com.laamella.c64_maze;

import org.junit.Test;

import com.laamella.daedalousy.generators.Eller;
import com.laamella.daedalousy.mazemodel.MatrixSquareTileMap;
import com.laamella.daedalousy.mazemodel.OrthogonalGrid;
import com.laamella.daedalousy.mazemodel.OrthogonalTileMap;
import com.laamella.daedalousy.mazemodel.OrtogonalTileMapToGridAdapter;

public class DaedalousyTester {
	@Test
	public void testEllerC64Generator() {
		final OrthogonalTileMap tileMap = new MatrixSquareTileMap(30, 20);
		final OrthogonalGrid grid = new OrtogonalTileMapToGridAdapter(tileMap);
		final Eller mazeProgram = new Eller(grid);
		mazeProgram.createMaze(0.5);
		System.out.println(tileMap);
	}

}
