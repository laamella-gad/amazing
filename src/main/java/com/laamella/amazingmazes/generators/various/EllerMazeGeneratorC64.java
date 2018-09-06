package com.laamella.amazingmazes.generators.various;

import com.laamella.amazingmazes.generators.GridMazeGenerator;
import com.laamella.amazingmazes.mazemodel.Position;
import com.laamella.amazingmazes.mazemodel.grid.Grid;
import com.laamella.amazingmazes.mazemodel.grid.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laamella.amazingmazes.mazemodel.grid.Direction.*;

/**
 * Converted from a BASIC type in listing, found in a magazine somewhere in the
 * eighties.
 *
 * <p>
 * The original listing follows. There may be typos in the part starting at line
 * 300.
 * <ul>
 * <li>CLS = reverse heart, clear screen
 * <li>] = bar on the right
 * <li>J = bar on the right and bottom
 * <li>. = block lower right
 * <li>_ = block on the bottom
 * <li>{Q} = cursor down
 * </ul>
 *
 * <pre>
 *  10 RUN80
 *  20 P=A-1
 *  30 IFR(P)THENP=R(P):GOTO30
 *  40 L(R(A))=P:R(P)=R(A):RETURN
 *  50 P=A
 *  60 IFL(P)THENP=L(P):GOTO60
 *  70 R(L(A-1))=P:L(P)=L(A-1):RETURN
 *  80 POKE53280,0:POKE53281,0:POKE646,8:PRINT&quot;CLS&quot;
 *  140 C$(0)=&quot;]&quot;:C$(1)=&quot;J&quot;:C$(2)=&quot;.&quot;:C$(3)=&quot;_&quot;:POKE53281,0
 *  150 PRINT:INPUT&quot;{Q}BREEDTE VAN DOOLHOF  (1-39)&quot;;X::IFX&lt;1ORX&gt;39THEN150
 *  160 PRINT:INPUT&quot;{Q}HOOGTE VAN DOOLHOF   (2-X)&quot;;H:IFH&lt;2THEN150
 *  170 INPUT&quot;STEILHEID ( 0 - .5 - 1 )&quot;;S:IFS&lt;0ORS&gt;1THEN170
 *  180 Q$=&quot;&quot;:IFH&lt;24THENINPUT&quot;OPLOSSEN?(J/N)&quot;;Q$:IFQ$&lt;&gt;&quot;J&quot;ANDQ$&lt;&gt;&quot;N&quot;THEN180
 *  190 PRINT&quot;CLS.&quot;;:DIML(X),R(X):I=INT(RND(1)*X)+1
 *  200 FORA=1TOX:PRINTC$(3+(A=I));:L(A)=0:R(A)=0:NEXT:PRINTSPC(39-X);&quot;]&quot;;
 *  210 FORY=2TOH:FORA=XTO1STEP-1:IFR(A)=A-1ORRND(1)&lt;STHENZ=0:GOTO250
 *  220 IFR(A)THENGOSUB20
 *  230 IFL(A-1)THENGOSUB50
 *  240 R(A)=A-1:L(A-1)=A:Z=2
 *  250 IFL(A)+R(A) AND RND(1)&gt;STHENL(R(A))=L(A):R(L(A))=R(A):L(A)=0:R(A)=0:Z=Z+1
 *  260 PRINTC$(Z);:NEXT:PRINTSPC(39-X);&quot;]&quot;;:NEXT
 *  270 U=INT(RND(1)*X)+1:FORA=XTO1STEP-1
 *  280 IFR(A)=A-1ORR(A)ANDRND(1)&lt;STHENPRINTC$(1+(A=U));:NEXT:GOTO300
 *  290 GOSUB20:PRINTC$(3+(A=U));:NEXT
 *  300 GETA$:IFA$=&quot;&quot;THEN300
 *  310 IFQ$&lt;&gt;&quot;J&quot;THENRUN140
 *  320 FORV=1024TO1103+40*H:POKEV,255ANDPEEK(V)-128:POKEV+54272,0:NEXT
 *  330 V=55296+I:U=55336+40*H:POkE53281,2:GOTO370
 *  340 IFPEEK(V-54273)AND128THENPOKEV,15AND15-PEEK(V-1):V=V-1:GOTO370
 *  350 IFNOTPEEK(V-54312)AND2THENPOKEV,15AND15-PEEK(V-40):V=V-40:GOTO340
 *  360 IFPEEK(V-54272)AND128THENPOKEV,15AND15-PEEK(V+1):V=V+1:GOTO350
 *  370 IFPEEK(U-54272)AND2THEN340
 *  380 POKEV,15AND15-PEEK(V+40):V=V+40:IFV&lt;UTHEN360
 *  390 FORA=0TO49:POKE53281,15AND23-PEEK(53281);FORB=0TO49:NEXT:NEXT
 *  400 Q$=&quot;&quot;:GOTO300
 * </pre>
 */
// TODO convert to progressive row based algorithm
public class EllerMazeGeneratorC64 implements GridMazeGenerator {
    private static Logger log = LoggerFactory.getLogger(EllerMazeGeneratorC64.class);

    private final double steepness;

    public EllerMazeGeneratorC64(final double steepness) {
        this.steepness = steepness;
    }

    private void messWithLR_0(final int x, final int[] l, final int[] r) {
        int pos = x - 1;

        while (r[pos] != 0) {
            pos = r[pos];
        }

        l[r[x]] = pos;
        r[pos] = r[x];
    }

    private void messWithLR_1(final int x, final int[] l, final int[] r) {
        int pos = x;

        while (l[pos] != 0) {
            pos = l[pos];
        }

        r[l[x - 1]] = pos;
        l[pos] = l[x - 1];
    }

    private void setSquareOpenRightAndBottom(final Grid grid, final int x, final int y, final boolean openRight,
                                             final boolean openBottom) {
        final Square square = grid.getSquare(new Position(x, y));
        square.getWall(RIGHT).setOpened(openRight);
        square.getWall(DOWN).setOpened(openBottom);
    }

    @Override
    public void generateMaze(final Grid plainGrid) {
        final Grid.UtilityWrapper grid = new Grid.UtilityWrapper(plainGrid);
        log.debug("generateMaze");

        final int width = grid.getSize().width;
        final int height = grid.getSize().height;
        final int[] left = new int[width + 1];
        final int[] right = new int[width + 1];

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
    }

    private boolean isOpenAtTheBottom(final double steepness, final int[] l, final int[] r, final int x) {
        if ((l[x] + r[x] != 0) && randomHorizontal(steepness)) {
            l[r[x]] = l[x];
            r[l[x]] = r[x];
            l[x] = 0;
            r[x] = 0;
            return false;
        }
        return true;
    }

    private boolean isOpenToTheRight(final double steepness, final int[] l, final int[] r, final int x) {
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

    private int random(final int width) {
        return (int) (Math.random() * width);
    }

    private boolean randomHorizontal(final double steepness) {
        return Math.random() > steepness;
    }

    private boolean randomVertical(final double steepness) {
        return Math.random() < steepness;
    }
}
