package com.laamella.daedalousy.generators;

public class DaedalusAldousBroder {
	public DaedalusAldousBroder() {

	}

	public void createMaze() {
		// int x, y, xnew, ynew, d;
		// long count;
		// flag fWall = ms.fTreeWall;
		//
		// if (!FEnsureMazeSize(pb, 4, femsEvenSize | femsEvenStart))
		// return fFalse;
		// b = *pb;
		// MazeClear(b, !fWall);
		// if (ms.fTreeWall)
		// Edge(b, xl, yl, xh-1, yh-1, fOn);
		// MakeEntranceExit(b, 0);
		// if (!fWall) {
		// x = Rnd(xl, xh-2) | 1; y = Rnd(yl, yh-2) | 1;
		// M0(b, x, y);
		// } else
		// x = y = 0;
		// count = (long)(((xh - xl) >> 1) - fWall) * (((yh - yl) >> 1) - fWall)
		// -
		// !fWall;
		// if (count <= 0)
		// return fTrue;
		// UpdateDisplay();
		//
		// // Randomly walk around on the Maze until all cells have been
		// visited.
		// loop {
		// d = RndDir();
		// xnew = x + xoff2[d]; ynew = y + yoff2[d];
		// if (!fWall) {
		// if (!FLegalMaze(xnew, ynew))
		// continue;
		// } else {
		//
		// // For wall added Mazes, when run into the boundary wall, teleport to
		// a
		// // random boundary wall vertex. The boundary wall should be treated
		// like
		// // one big vertex to create all Mazes with equal probability.
		// if (xnew <= xl || xnew >= xh-1 || ynew <= yl || ynew >= yh-1) {
		// if (Rnd(0, ((MX(b) + MY(b)) >> 1) - 3) < MX(b) >> 1) {
		// x = Rnd(xl, xh) & ~1;
		// y = Rnd(0, 1) ? yl : yh-1;
		// } else {
		// x = Rnd(0, 1) ? xl : xh-1;
		// y = Rnd(yl+2, yh-2) & ~1;
		// }
		// continue;
		// }
		// }
		//
		// // When moving to an unvisited cell, extend the Maze into it.
		// if (MG(b, xnew, ynew) != fWall) {
		// MP(b, (x + xnew) >> 1, (y + ynew) >> 1, fWall);
		// MP(b, xnew, ynew, fWall);
		// count--;
		// if (count <= 0)
		// break;
		// }
		// x = xnew; y = ynew;
		// }
		//
	}
}
