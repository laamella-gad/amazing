package com.laamella.amazingmazes;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				new MazeFrame().setVisible(true);
			}
		});
	}
}
