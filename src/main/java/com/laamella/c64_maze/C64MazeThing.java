package com.laamella.c64_maze;

import java.awt.EventQueue;
import java.io.IOException;

public class C64MazeThing {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				MazeFrame mazeFrame;
				try {
					mazeFrame = new MazeFrame();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				mazeFrame.setVisible(true);
			}			
		});
	}
}
