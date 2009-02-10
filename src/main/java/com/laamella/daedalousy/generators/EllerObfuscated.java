package com.laamella.daedalousy.generators;

public class EllerObfuscated {
// char M[3];		// holds the 2 characters printed for each cell
//int H,			// height of the maze
//    C,			// current cell
//    E,			// temporary pointer used in the updating
//    L[40],R[40];        // left and right pointers
//
//main()
//{
//  L[0] = scanf("%d",&H);		/* reads height and sets L[0] to 1 */
//  for (E = 40; --E; L[E] = R[E] = E)
//    printf("._");			/* close top of maze */
//  printf("\n|");
//  while (--H)                           /* more rows to do */
//  { for (C = 40; --C; printf(M))	/* visit cells from left to right */
//    { if (C != (E=L[C-1]) && 6<<27<rand())	/* make right-connection ? */
//      { R[E] = R[C];			/* link E */
//        L[R[C]] = E;			/* to R[C] */
//        R[C] = C-1;			/* link C */
//        L[C-1] = C;			/* to C-1 */
//        M[1] = '.';			/* no wall to the right */
//      }
//      else M[1] = '|';			/* wall to the right */
//      if (C != (E=L[C]) && 6<<27<rand()) 	/* omit down-connection ? */
//      { R[E] = R[C];			/* link E */
//        L[R[C]] = E;			/* to R[C] */
//        L[C] = C;			/* link C */
//        R[C] = C;			/* to C */
//        M[0] = '_';			/* wall downward */
//      }
//      else M[0] = ' ';			/* no wall downward */
//    }
//    printf("\n|");
//  }
//  M[0] = '_';				/* close bottom of maze */
//  for (C = 40; --C; printf(M))		/* bottom row */
//  { if (C != (E=L[C-1]) && (C == R[C] || 6<<27<rand()))
//    { L[R[E]=R[C]]=E;
//      L[R[C]=C-1]=C;
//      M[1] = '.';
//    }
//    else M[1] = '|';
//    E = L[C];
//    R[E] = R[C];
//    L[R[C]] = E;
//    L[C] = C;
//    R[C] = C;
//  }
//  printf("\n");
//}

}
