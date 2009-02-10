package com.laamella.daedalousy.generators;

import com.laamella.daedalousy.mazemodel.SquareGrid;
import com.laamella.daedalousy.mazemodel.SquareGrid.Wall;

public class EllerObfuscated {
	private SquareGrid model;

	public EllerObfuscated(SquareGrid model) {
		this.model = model;
	}

	public void createMaze() {
		int height=model.getHeightInSquares();
		int width=model.getWidthInSquares();
		int H; // height of the maze
		int C; // current cell
		int E; // temporary pointer used in the updating
		int L[] = new int[width+1];// left pointers
		int R[] = new int[width+1]; // right pointers
		
		boolean wallToTheRight;
		boolean wallDownward;

		L[0] = 1;
		H = model.getHeightInSquares();
		for (E = width; E > 0; --E, L[E] = R[E] = E) {
			model.setMazeWall(E, 0, Wall.SOUTH, true);
		}
		model.setMazeWall(0, 1, Wall.EAST, true);
		while (H-- > 0) // more rows to do
		{
			// visit cells from left to right
			for (C = width; C > 0; --C) {
				// make right-connection?
				if (C != (E = L[C - 1]) && 6 << 27 < Math.random()) 
				{
					R[E] = R[C]; // link E
					L[R[C]] = E; // to R[C]
					R[C] = C - 1; // link C
					L[C - 1] = C; // to C-1
					wallToTheRight=false;
				} else {
					wallToTheRight=true;
				}

				// omit down-connection?
				if (C != (E = L[C]) && 6 << 27 < Math.random()) {
					R[E] = R[C]; // link E
					L[R[C]] = E; // to R[C]
					L[C] = C; // link C
					R[C] = C; // to C
					wallDownward=true;
				} else {
					wallDownward=false;
				}
				model.setMazeWall(width-C, height-H, Wall.EAST, wallToTheRight);
				model.setMazeWall(width-C, height-H, Wall.SOUTH, wallDownward);
			}
//			printf("\n|");
		}
//		M[0] = '_'; // close bottom of maze
//		for (C = 40; C > 0; C--, printf(M)) // bottom row
//		{
//			if (C != (E = L[C - 1]) && (C == R[C] || 6 << 27 < Math.random())) {
//				L[R[E] = R[C]] = E;
//				L[R[C] = C - 1] = C;
//				M[1] = '.';
//			} else {
//				M[1] = '|';
//			}
//			E = L[C];
//			R[E] = R[C];
//			L[R[C]] = E;
//			L[C] = C;
//			R[C] = C;
//		}
//		printf("\n");
	}
}
