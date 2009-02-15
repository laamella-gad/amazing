package com.laamella.amazing.generators;

/**
 * This algorithm is nice because it requires no extra storage or stack, and is
 * therefore suited to creating the largest Mazes or Mazes on the most limited
 * systems, since there are no issues of running out of memory. Since there are
 * no rules that must be followed all the time, it's also the easiest to modify
 * and to get to create Mazes of different textures. It's most similar to the
 * recursive backtracker, except when there's no unmade cell next to the current
 * position, you enter "hunting" mode, and systematically scan over the Maze
 * until an unmade cell is found next to an already carved into cell, at which
 * point you start carving again at that new location. The Maze is done when all
 * cells have been scanned over once in "hunt" mode. This algorithm tends to make
 * Mazes with a high "river" factor, but not as high as the recursive
 * backtracker. You can make this generate Mazes with a lower river factor by
 * choosing to enter "hunt" mode more often. It runs slower due to the time spent
 * hunting for the last cells, but isn't much slower than Kruskal's algorithm.
 * This can be done as a wall adder if you randomly teleport on occasion, to
 * avoid the issues the recursive backtracker has.
 * <p>http://www.astrolog.org/labyrnth/Maze.java
 */
public class HuntAndKillMazeGenerator implements MazeGenerator{

	public void generateMaze() {
		// TODO Auto-generated method stub
		
	}
	// MazeMaker 1.0 - By Walter D. Pullen - Dec 6, 1996

//	private void mazestart()
//	{
//		int x, y, i;
//		int xnew = 0, ynew = 0, dirx = 2, diry = 2, count = xsiz*ysiz - 1, d;
//		boolean hunt = false;
//
//		xcel = (xmax-xwid) / xsiz;
//		ycel = (ymax-xwid) / ysiz;
//		xoff = (xmax - xsiz*xcel - xwid) / 2;
//		yoff = (ymax - ysiz*ycel - ywid) / 2;
//
//		for (i = 0; i < xmaz*ymaz; i++)
//			rgfCell[i] = true;
//		for (y = 0; y < ymaz; y++)
//			set(xmaz - 1, y);
//		for (x = 0; x < xmaz; x++)
//			set(x, ymaz - 1);
//
//		xlocorg = rnd(xsiz)*2 + 1; xloc = xlocorg;
//		yloc = 1; wdir = 2; fDot = false;
//
//		x = xloc; y = yloc;
//		set(x, 0); set(x, y);
//		do {
//			d = rnd(4);
//			if (!fmap(x, y)) {
//				for (i = 0; i < 4; i++) {
//					xnew = x + xinc[d]*2;
//					ynew = y + yinc[d]*2;
//					if (xnew > 0 && ynew > 0 && xnew < xmaz-1 && ynew < ymaz-1 &&
//						fmap(xnew, ynew))
//						break;
//					d++; d &= 3;
//				}
//				hunt = (i >= 4);
//			}
//			if (!hunt) {
//				set(x+xnew >> 1, y+ynew >> 1);
//				set(xnew, ynew);
//				x = xnew; y = ynew;
//				count--;
//			} else {
//				if (x + dirx >= 0 && x + dirx < xmaz-1)
//					x += dirx;
//				else {
//					dirx = -dirx;
//					if (y + diry >= 0 && y + diry < ymaz-1)
//						y += diry;
//					else
//						diry = -diry;
//				}
//			}
//		} while (count > 0);
//		set(rnd(xsiz)*2 + 1, ymaz-2);
//	}
//

}
