package com.laamella.c64_maze;

import org.junit.Test;

import com.laamella.daedalousy.generators.EllerC64;
import com.laamella.daedalousy.mazemodel.MatrixSquareTileMap;
import com.laamella.daedalousy.mazemodel.SquareGrid;
import com.laamella.daedalousy.mazemodel.SquareTileMap;
import com.laamella.daedalousy.mazemodel.TileMapToSquareGridAdapter;

public class DaedalousyTester {
	@Test
	public void testEllerC64() {
		final SquareTileMap tileMap = new MatrixSquareTileMap(30, 20);
		final SquareGrid grid = new TileMapToSquareGridAdapter(tileMap);
		final EllerC64 mazeProgram = new EllerC64(grid);
		mazeProgram.createMaze(0.5);
		System.out.println(tileMap);
	}
}
