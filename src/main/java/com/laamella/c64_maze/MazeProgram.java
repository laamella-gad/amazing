package com.laamella.c64_maze;

import static com.laamella.c64_maze.C64.DEBOOL;
import static com.laamella.c64_maze.C64.ENBOOL;
import static com.laamella.c64_maze.C64.INT;
import static com.laamella.c64_maze.C64.RND;

import javax.swing.SwingWorker;

public class MazeProgram extends SwingWorker<String, Object> {
	private int X;
	private int H;
	private float S;
	private int[] L;
	private int[] R;
	private int I;
	private int Z;
	private int P;
	private int A;
	private int Y;
	private int U;
	private String Q$;

	private final C64 c64;

	public MazeProgram(final C64 c64) {
		this.c64 = c64;
	}

	private void GOSUB20() {
		// 20 P=A-1
		P = A - 1;

		// 30 IFR(P)THENP=R(P):GOTO30
		while (DEBOOL(R[P])) { // ???
			P = R[P];
		}

		// 40 L(R(A))=P:R(P)=R(A):RETURN
		L[R[A]] = P;
		R[P] = R[A];
		return;
	}

	private void GOSUB50() {
		// 50 P=A
		P = A;

		// 60 IFL(P)THENP=L(P):GOTO60
		while (DEBOOL(L[P])) { // ???
			P = L[P];
		}

		// 70 R(L(A-1))=P:L(P)=L(A-1):RETURN
		R[L[A - 1]] = P;
		L[P] = L[A - 1];
		return;
	}

	public void RUN() {
		// CLS = reverse heart, clear screen
		// ] = bar on the right
		// J = bar on the right and bottom
		// . = block lower right
		// _ = block on the bottom
		// {Q} = cursor down

		// 10 RUN80
		RUN80();
	}

	private void RUN80() {
		// 80 POKE53280,0:POKE53281,0:POKE646,8:PRINT"CLS"
		c64.POKE(53280, 0);
		c64.POKE(53281, 0);
		c64.POKE(646, 8);
		c64.CLS();

		final int[] C$ = new int[10];
		// 140 C$(0)="]":C$(1)="J":C$(2)=".":C$(3)="_":POKE53281,0
		C$[0] = 0xE1;
		C$[1] = 0xFE;
		C$[2] = 0x6C;
		C$[3] = 0x62;
		c64.POKE(53281, 0);

		X = 39;
		H = 22;
		S = 0.5f;
		Q$ = "J";
		// // 150
		// PRINT:INPUT"{Q}BREEDTE VAN DOOLHOF  (1-39)";X::IFX<1ORX>39THEN150
		// X =
		// Integer.parseInt(JOptionPane.showInputDialog("BREEDTE VAN DOOLHOF (1-39)",
		// "20"));
		// // 160 PRINT:INPUT"{Q}HOOGTE VAN DOOLHOF   (2-X)";H:IFH<2THEN150
		// H =
		// Integer.parseInt(JOptionPane.showInputDialog("HOOGTE VAN DOOLHOF   (2-X)",
		// "15"));
		//
		// // 170 INPUT"STEILHEID ( 0 - .5 - 1 )";S:IFS<0ORS>1THEN170
		// S =
		// Float.parseFloat(JOptionPane.showInputDialog("STEILHEID ( 0 - .5 - 1 )",
		// "0.5"));
		//
		// // 180
		// //
		// Q$="":IFH<24THENINPUT"OPLOSSEN?(J/N)";Q$:IFQ$<>"J"ANDQ$<>"N"THEN180
		// Q$=JOptionPane.showInputDialog("OPLOSSEN?(J/N)", "J");

		// 190 PRINT"CLS.";:DIML(X),R(X):I=INT(RND(1)*X)+1
		c64.CLS();
		c64.PRINT_POKE_SEMICOLON(0x6C);
		L = new int[X + 1];
		R = new int[X + 1];
		I = INT(RND(1) * X) + 1;

		// 200
		// FORA=1TOX:PRINTC$(3+(A=I));:L(A)=0:R(A)=0:NEXT:PRINTSPC(39-X);"]";
		for (A = 1; A <= X; A++) {
			c64.PRINT_POKE_SEMICOLON(C$[3 + (ENBOOL(A == I))]);
			L[A] = 0;
			R[A] = 0;
		}
		c64.PRINT_SPC_SEMICOLON(39 - X);
		c64.PRINT_POKE_SEMICOLON(0xE1);

		Z = 0;
		// 210 FORY=2TOH:FORA=XTO1STEP-1:IFR(A)=A-1ORRND(1)<STHENZ=0:GOTO250
		for (Y = 2; Y <= H; Y++) {
			for (A = X; A >= 1; A--) {
				if (R[A] == A - 1 || RND(1) < S) {
					Z = 0;
				} else {

					// 220 IFR(A)THENGOSUB20
					if (DEBOOL(R[A])) {
						GOSUB20();
					}

					// 230 IFL(A-1)THENGOSUB50
					if (DEBOOL(L[A - 1])) {
						GOSUB50();
					}

					// 240 R(A)=A-1:L(A-1)=A:Z=2
					R[A] = A - 1;
					L[A - 1] = A;
					Z = 2;
				}
				// 250 IFL(A)+R(A) AND
				// RND(1)>STHENL(R(A))=L(A):R(L(A))=R(A):L(A)=0:R(A)=0:Z=Z+1
				if (DEBOOL(L[A] + R[A]) && RND(1) > S) {
					L[R[A]] = L[A];
					R[L[A]] = R[A];
					L[A] = 0;
					R[A] = 0;
					Z = Z + 1;
				}

				// 260 PRINTC$(Z);:NEXT:PRINTSPC(39-X);"]";:NEXT
				c64.PRINT_POKE_SEMICOLON(C$[Z]);
			}
			c64.PRINT_SPC_SEMICOLON(39 - X);
			c64.PRINT_POKE_SEMICOLON(0xE1);
		}
		// 270 U=INT(RND(1)*X)+1:FORA=XTO1STEP-1
		U = INT(RND(1) * X) + 1;
		for (A = X; A >= 1; A--) {

			// 280
			// IF R(A)=A-1 OR R(A) AND RND(1)<S
			// THENPRINTC$(1+(A=U));:NEXT:GOTO300
			if (R[A] == A - 1 || DEBOOL(R[A]) && RND(1) < S) {
				c64.PRINT_POKE_SEMICOLON(C$[1 + (ENBOOL(A == U))]);

			} else {
				// 290 GOSUB20:PRINTC$(3+(A=U));:NEXT
				GOSUB20();
				c64.PRINT_POKE_SEMICOLON(C$[3 + (ENBOOL(A == U))]);
			}
		}
	}

	@Override
	protected String doInBackground() throws Exception {
		RUN();
		return "";
	}

}
