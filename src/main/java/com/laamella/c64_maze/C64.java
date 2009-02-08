package com.laamella.c64_maze;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class C64 extends JPanel {
	private static final int START_OF_SCREEN_MEMORY = 1024;

	private static final long serialVersionUID = 1L;

	private static final int ScreenWidth = 40;

	private static final int ScreenHeight = 25;

	public static final int TRUE = -1;

	public static final int FALSE = 0;

	private final int[] memory = new int[0x10000];

	private int cursorX = 0;

	private int cursorY = 0;

	private final BufferedImage characterSet;

	public C64() throws IOException {
		characterSet = ImageIO.read(new File("c64_charset.png"));
		CLS();
		setSize(640, 400);
		setMinimumSize(new Dimension(640, 400));
		final Timer repaintTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		repaintTimer.start();
	}

	public void CLS() {
		for (int i = START_OF_SCREEN_MEMORY; i < START_OF_SCREEN_MEMORY + ScreenWidth * ScreenHeight; i++) {
			POKE(i, 0x20);
		}
		cursorX = 0;
		cursorY = 0;
	}

	public int BarLeft4Pixels = 0x61;

	protected void paintComponent(Graphics g) {
		System.out.println("repaint");
		super.paintComponent(g);
		final Graphics2D graphics = (Graphics2D) g;
		for (int x = 0; x < ScreenWidth; x++) {
			for (int y = 0; y < ScreenHeight; y++) {
				paintCharacter(graphics, x, y, getCharAt(x, y));
			}
		}
	}

	private void paintCharacter(Graphics2D graphics, int x, int y, int character) {
		final int characterX = character % 16;
		final int characterY = character / 16;
		graphics.drawImage(characterSet, x * 16, y * 16, x * 16 + 16, y * 16 + 16, characterX * 16, characterY * 16, characterX * 16 + 16,
				characterY * 16 + 16, null);
	}

	public int PEEK(int address) {
		return memory[address];
	}

	public void POKE(int address, int value) {
		memory[address] = value;
	}

	public int getCharAt(int x, int y) {
		return memory[START_OF_SCREEN_MEMORY + x + ScreenWidth * y];
	}

	public void setCharAt(int x, int y, int character) {
		System.out.print((char)character);
		memory[START_OF_SCREEN_MEMORY + x + ScreenWidth * y] = character;
	}

	public void PRINT(String text) {
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				PRINT_POKE_SEMICOLON(ch - 'a' + 0x01);
			} else if (ch >= 'A' && ch <= 'Z') {
				PRINT_POKE_SEMICOLON(ch - 'A' + 0x01);
			} else if (ch >= '0' && ch <= '9') {
				PRINT_POKE_SEMICOLON(ch - '0' + 0x30);
			} else {
				PRINT_POKE_SEMICOLON(ch);
			}
		}
		PRINT();
	}

	public void PRINT() {
		cursorX = 0;
		CRSR_DOWN();
		System.out.println();
	}

	private void SCROLL() {
		// TODO Auto-generated method stub
	}

	public void PRINT_POKE_SEMICOLON(int memoryCode) {
		setCharAt(cursorX, cursorY, memoryCode);
		cursorX++;
		if (cursorX >= ScreenWidth) {
			PRINT();
		}
	}

	public void CRSR_DOWN() {
		cursorY++;
		if (cursorY >= ScreenHeight) {
			SCROLL();
		}
	}

	public void CRSR_RIGHT() {
		System.out.print(" ");
		cursorX++;
		if (cursorX >= ScreenWidth) {
			if (cursorY >= ScreenHeight) {
				cursorX = ScreenWidth - 1;
			} else {
				cursorX = 0;
				cursorY++;
			}
		}
	}

	public void CRSR_LEFT() {
		cursorX--;
		if (cursorX < 0) {
			if (cursorY == 0) {
				cursorX = 0;
			} else {
				cursorX = ScreenWidth - 1;
				cursorY--;
			}
		}
	}

	public void CRSR_UP() {
		if (cursorY > 0) {
			cursorY--;
		}
	}

	public static float RND(int i) {
		return (float) Math.random();
	}

	public static int INT(float i) {
		return (int) i;
	}

	public static int ENBOOL(boolean b) {
		return b ? TRUE : FALSE;
	}

	public static boolean DEBOOL(int b) {
		if(b==FALSE){
			return false;
		}
		return true;
	}

	public void PRINT_SPC_SEMICOLON(int spaces) {
		for (int i = 0; i < spaces; i++) {
			CRSR_RIGHT();
		}
	}

}
