package com.laamella.c64_maze;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class C64 extends JPanel {
	private static final int START_OF_SCREEN_MEMORY = 1024;

	private static final long serialVersionUID = 1L;

	private static final int ScreenWidth = 40;

	private static final int ScreenHeight = 25;

	private final int[] memory = new int[0x10000];

	private int cursorX = 0;

	private int cursorY = 0;

	private final BufferedImage characterSet;

	public C64() throws IOException {
		characterSet = ImageIO.read(new File("c64_charset.png"));
		cls();
		setSize(640, 400);
		setMinimumSize(new Dimension(640, 400));
	}

	public void cls() {
		for (int i = START_OF_SCREEN_MEMORY; i < START_OF_SCREEN_MEMORY + ScreenWidth * ScreenHeight; i++) {
			poke(i, 0x20);
		}
		cursorX = 0;
		cursorY = 0;
	}

	public int BarLeft4Pixels = 0x61;

	protected void paintComponent(Graphics g) {
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

	public int peek(int address) {
		return memory[address];
	}

	public void poke(int address, int value) {
		memory[address] = value;
	}

	public int getCharAt(int x, int y) {
		return memory[START_OF_SCREEN_MEMORY + x + ScreenWidth * y];
	}

	public void setCharAt(int x, int y, int character) {
		memory[START_OF_SCREEN_MEMORY + x + ScreenWidth * y] = character;
	}

	public void printAscii(String text) {
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				printMemoryCode(ch - 'a' + 0x01);
			} else if (ch >= 'A' && ch <= 'Z') {
				printMemoryCode(ch - 'A' + 0x01);
			} else if (ch >= '0' && ch <= '9') {
				printMemoryCode(ch - '0' + 0x30);
			} else {
				printMemoryCode(ch);
			}
		}
		print();
	}

	public void print() {
		cursorX = 0;
		cursorDown();
	}

	private void scroll() {
		// TODO Auto-generated method stub
	}

	public void printMemoryCode(int memoryCode) {
		setCharAt(cursorX, cursorY, memoryCode);
		cursorX++;
		if (cursorX >= ScreenWidth) {
			print();
		}
	}

	public void cursorDown() {
		cursorY++;
		if (cursorY >= ScreenHeight) {
			scroll();
		}
	}

	public void cursorRight() {
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

	public void cursorLeft() {
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

	public void cursorUp() {
		if (cursorY > 0) {
			cursorY--;
		}
	}
}
