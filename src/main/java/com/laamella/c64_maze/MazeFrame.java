package com.laamella.c64_maze;

import java.io.IOException;

import javax.swing.JFrame;

public class MazeFrame extends JFrame {

	private final C64 c64;


	public MazeFrame() throws IOException {
		setSize(700, 500);
		c64 = new C64();
		setContentPane(c64);
		c64.PRINT();
		c64.PRINT("    **** COMMODORE 64 BASIC V2 ****");
		c64.PRINT();
		c64.PRINT(" 64K RAM SYSTEM 38911 BASIC BYTES FREE");
		c64.PRINT();
		c64.PRINT("READY.");
		MazeProgram mazeProgram = new MazeProgram(c64);
		mazeProgram.execute();
	}

}
