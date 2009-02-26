package com.laamella.amazing.generators.various;

import static com.laamella.amazing.mazemodel.orthogonal.Direction.*;

import org.grlea.log.SimpleLogger;

import com.laamella.amazing.generators.GridMazeGenerator;
import com.laamella.amazing.mazemodel.Position;
import com.laamella.amazing.mazemodel.orthogonal.Grid;
import com.laamella.amazing.mazemodel.orthogonal.Square;

/**
 * Converted from a BASIC type in listing, found in a magazine somewhere in the
 * eighties.
 */
// TODO convert to progressive row based algorithm
public class EllerMazeGeneratorC64 implements GridMazeGenerator {
	private static final SimpleLogger log = new SimpleLogger(EllerMazeGeneratorC64.class);
	private final double steepness;

	public EllerMazeGeneratorC64(final double steepness) {
		this.steepness = steepness;
	}

	private void messWithLR_0(int x, int[] l, int[] r) {
		int pos = x - 1;

		while (r[pos] != 0) {
			pos = r[pos];
		}

		l[r[x]] = pos;
		r[pos] = r[x];
	}

	private void messWithLR_1(int x, int[] l, int[] r) {
		int pos = x;

		while (l[pos] != 0) {
			pos = l[pos];
		}

		r[l[x - 1]] = pos;
		l[pos] = l[x - 1];
	}

	private void setSquareOpenRightAndBottom(final Grid grid, int x, int y, boolean openRight, boolean openBottom) {
		final Square square = grid.getSquare(new Position(x, y));
		square.getWall(RIGHT).setOpened(openRight);
		square.getWall(DOWN).setOpened(openBottom);
	}

	@Override
	public void generateMaze(final Grid plainGrid) {
		final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
		log.entry("generateMaze");

		final int width = grid.getSize().width;
		final int height = grid.getSize().height;
		final int[] left = new int[width + 1];
		final int[] right = new int[width + 1];

		grid.closeAllWalls();

		log.debug("Top entrance");
		grid.getSquare(new Position(random(width), 0)).getWall(UP).open();

		// Print maze
		for (int y = 1; y < height; y++) {
			log.debug("New row");
			for (int x = width; x >= 1; x--) {
				final boolean openRight = isOpenToTheRight(steepness, left, right, x);
				final boolean openBottom = isOpenAtTheBottom(steepness, left, right, x);
				setSquareOpenRightAndBottom(grid, width - x, y - 1, openRight, openBottom);
			}
		}

		log.debug("Finishing maze with last row");
		setSquareOpenRightAndBottom(grid, 0, height - 1, false, true);
		final int exitX = random(width) + 1;

		for (int x = width; x >= 1; x--) {
			if (right[x] == x - 1 || (right[x] != 0) && randomVertical(steepness)) {
				setSquareOpenRightAndBottom(grid, width - x, height - 1, false, x == exitX);
			} else {
				messWithLR_0(x, left, right);
				setSquareOpenRightAndBottom(grid, width - x, height - 1, true, x == exitX);
			}
		}
		log.exit("generateMaze");
	}

	private boolean isOpenAtTheBottom(double steepness, final int[] l, final int[] r, int x) {
		if ((l[x] + r[x] != 0) && randomHorizontal(steepness)) {
			l[r[x]] = l[x];
			r[l[x]] = r[x];
			l[x] = 0;
			r[x] = 0;
			return false;
		}
		return true;
	}

	private boolean isOpenToTheRight(double steepness, final int[] l, final int[] r, int x) {
		if (r[x] == x - 1 || randomVertical(steepness)) {
			return false;
		}
		if (r[x] != 0) {
			messWithLR_0(x, l, r);
		}
		if (l[x - 1] != 0) {
			messWithLR_1(x, l, r);
		}
		r[x] = x - 1;
		l[x - 1] = x;
		return true;
	}

	private int random(int width) {
		return (int) (Math.random() * width);
	}

	private boolean randomHorizontal(double steepness) {
		return Math.random() > steepness;
	}

	private boolean randomVertical(double steepness) {
		return Math.random() < steepness;
	}
}

// CLS = reverse heart, clear screen
// ] = bar on the right
// J = bar on the right and bottom
// . = block lower right
// _ = block on the bottom
// {Q} = cursor down

// 10 RUN80
// 20 P=A-1
// 30 IFR(P)THENP=R(P):GOTO30
// 40 L(R(A))=P:R(P)=R(A):RETURN
// 50 P=A
// 60 IFL(P)THENP=L(P):GOTO60
// 70 R(L(A-1))=P:L(P)=L(A-1):RETURN
// 80 POKE53280,0:POKE53281,0:POKE646,8:PRINT"CLS"
// 140 C$(0)="]":C$(1)="J":C$(2)=".":C$(3)="_":POKE53281,0
// 150 PRINT:INPUT"{Q}BREEDTE VAN DOOLHOF  (1-39)";X::IFX<1ORX>39THEN150
// 160 PRINT:INPUT"{Q}HOOGTE VAN DOOLHOF   (2-X)";H:IFH<2THEN150
// 170 INPUT"STEILHEID ( 0 - .5 - 1 )";S:IFS<0ORS>1THEN170
// 180 Q$="":IFH<24THENINPUT"OPLOSSEN?(J/N)";Q$:IFQ$<>"J"ANDQ$<>"N"THEN180
// 190 PRINT"CLS.";:DIML(X),R(X):I=INT(RND(1)*X)+1
// 200 FORA=1TOX:PRINTC$(3+(A=I));:L(A)=0:R(A)=0:NEXT:PRINTSPC(39-X);"]";
// 210 FORY=2TOH:FORA=XTO1STEP-1:IFR(A)=A-1ORRND(1)<STHENZ=0:GOTO250
// 220 IFR(A)THENGOSUB20
// 230 IFL(A-1)THENGOSUB50
// 240 R(A)=A-1:L(A-1)=A:Z=2
// 250 IFL(A)+R(A) AND RND(1)>STHENL(R(A))=L(A):R(L(A))=R(A):L(A)=0:R(A)=0:Z=Z+1
// 260 PRINTC$(Z);:NEXT:PRINTSPC(39-X);"]";:NEXT
// 270 U=INT(RND(1)*X)+1:FORA=XTO1STEP-1
// 280 IFR(A)=A-1ORR(A)ANDRND(1)<STHENPRINTC$(1+(A=U));:NEXT:GOTO300
// 290 GOSUB20:PRINTC$(3+(A=U));:NEXT
// 300 GETA$:IFA$=""THEN300
// 310 IFQ$<>"J"THENRUN140
// 320 FORV=1024TO1103+40*H:POKEV,255ANDPEEK(V)-128:POKEV+54272,0:NEXT
// 330 V=55296+I:U=55336+40*H:POkE53281,2:GOTO370
// 340 IFPEEK(V-54273)AND128THENPOKEV,15AND15-PEEK(V-1):V=V-1:GOTO370
// 350 IFNOTPEEK(V-54312)AND2THENPOKEV,15AND15-PEEK(V-40):V=V-40:GOTO340
// 360 IFPEEK(V-54272)AND128THENPOKEV,15AND15-PEEK(V+1):V=V+1:GOTO350
// 370 IFPEEK(U-54272)AND2THEN340
// 380 POKEV,15AND15-PEEK(V+40):V=V+40:IFV<UTHEN360
// 390 FORA=0TO49:POKE53281,15AND23-PEEK(53281);FORB=0TO49:NEXT:NEXT
// 400 Q$="":GOTO300
